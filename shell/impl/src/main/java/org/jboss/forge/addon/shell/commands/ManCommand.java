/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.shell.commands;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.aesh.console.Console;
import org.jboss.aesh.extensions.manual.Man;
import org.jboss.aesh.parser.Parser;
import org.jboss.forge.addon.shell.CommandManager;
import org.jboss.forge.addon.shell.aesh.AbstractShellInteraction;
import org.jboss.forge.addon.shell.ui.AbstractShellCommand;
import org.jboss.forge.addon.shell.ui.ShellContext;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.input.InputComponent;
import org.jboss.forge.addon.ui.input.UICompleter;
import org.jboss.forge.addon.ui.input.UIInputMany;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Metadata;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class ManCommand extends AbstractShellCommand
{
   private CommandManager commandManager;

   @Inject
   private UIInputMany<String> arguments;

   @Inject
   public ManCommand(CommandManager commandManager)
   {
      this.commandManager = commandManager;
   }

   @Override
   public Metadata getMetadata()
   {
      return super.getMetadata().name("man")
               .description("man - an interface to the online reference manuals");
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      arguments.setDefaultValue(Arrays.asList(getMetadata().getName()));
      arguments.setLabel("");
      arguments.setRequired(false);

      arguments.setCompleter(new UICompleter<String>()
      {
         @Override
         public Iterable<String> getCompletionProposals(UIContext context, InputComponent<?, String> input, String value)
         {
            List<String> manCommands = new ArrayList<String>();
            // list all commands
            if (value == null || value.trim().length() < 1)
            {
               Map<String, AbstractShellInteraction> enabledShellCommands = commandManager
                        .getEnabledShellCommands((ShellContext) context);
               manCommands.addAll(enabledShellCommands.keySet());
            }
            // find the last
            else
            {
               String item = Parser.findEscapedSpaceWordCloseToEnd(value.trim());
               Collection<AbstractShellInteraction> matchingCommands = commandManager.findMatchingCommands(
                        (ShellContext) context, item);
               for (AbstractShellInteraction cmd : matchingCommands)
               {
                  manCommands.add(cmd.getName());
               }
            }

            return manCommands;
         }
      });
      builder.add(arguments);
   }

   @Override
   public Result execute(ShellContext context) throws Exception
   {
      Console console = context.getProvider().getConsole();
      try
      {
         Man man = new Man(console);
         // for now we only try to display the first
         String commandName = arguments.getValue().iterator().next();
         AbstractShellInteraction shellCommand = commandManager.findCommand(context, commandName);
         URL docUrl = shellCommand.getSourceCommand().getMetadata().getDocLocation();
         if (docUrl != null)
         {
            man.setFile(docUrl.openStream(), docUrl.getPath());
            man.attach(context.getConsoleOperation());
         }
         else
            console.out().println("No manual page found for: " + commandName);

      }
      catch (Exception ioe)
      {
         return Results.fail(ioe.getMessage());
      }
      return Results.success();
   }
}
