/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.ui.input;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;

import org.jboss.forge.addon.convert.Converter;
import org.jboss.forge.addon.facets.FacetNotFoundException;
import org.jboss.forge.addon.ui.context.UIValidationContext;
import org.jboss.forge.addon.ui.facets.HintsFacet;
import org.jboss.forge.addon.ui.validate.UIValidator;
import org.jboss.forge.furnace.spi.ListenerRegistration;
import org.jboss.forge.furnace.util.Assert;

/**
 * This class allows decoration of {@link UIInputMany} objects
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public abstract class AbstractUIInputManyDecorator<VALUETYPE> implements UIInputMany<VALUETYPE>
{

   private UIInputMany<VALUETYPE> delegate;

   /**
    * Create the delegate {@link UIInputMany} to be used in this implementation
    * 
    * @return a new instance of the {@link UIInputMany} object
    */
   protected abstract UIInputMany<VALUETYPE> createDelegate();

   /**
    * @return the delegate bound to this decorator. Never a <code>null</code> reference.
    * @throws IllegalArgumentException if the delegate returned by {@link #createDelegate()} is <code>null</code> or the
    *            same reference as this decorator class
    */
   protected final UIInputMany<VALUETYPE> getDelegate()
   {
      if (delegate == null)
      {
         delegate = createDelegate();
         Assert.notNull(delegate, "Delegate cannot be null");
         Assert.isTrue(delegate != this, "Decorator cannot delegate to itself");
      }
      return delegate;
   }

   @Override
   public String getLabel()
   {
      return getDelegate().getLabel();
   }

   @Override
   public String getName()
   {
      return getDelegate().getName();
   }

   @Override
   public String getDescription()
   {
      return getDelegate().getDescription();
   }

   @Override
   public Class<VALUETYPE> getValueType()
   {
      return getDelegate().getValueType();
   }

   @Override
   public boolean hasDefaultValue()
   {
      return getDelegate().hasDefaultValue();
   }

   @Override
   public boolean hasValue()
   {
      return getDelegate().hasValue();
   }

   @Override
   public boolean isEnabled()
   {
      return getDelegate().isEnabled();
   }

   @Override
   public boolean isRequired()
   {
      return getDelegate().isRequired();
   }

   @Override
   public String getRequiredMessage()
   {
      return getDelegate().getRequiredMessage();
   }

   @Override
   public char getShortName()
   {
      return getDelegate().getShortName();
   }

   @Override
   public Set<UIValidator> getValidators()
   {
      return getDelegate().getValidators();
   }

   @Override
   public UIInputMany<VALUETYPE> setEnabled(boolean b)
   {
      getDelegate().setEnabled(b);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setEnabled(Callable<Boolean> callable)
   {
      getDelegate().setEnabled(callable);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setLabel(String label)
   {
      getDelegate().setLabel(label);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setDescription(String description)
   {
      getDelegate().setDescription(description);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setDescription(Callable<String> description)
   {
      getDelegate().setDescription(description);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setRequired(boolean required)
   {
      getDelegate().setRequired(required);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setRequired(Callable<Boolean> required)
   {
      getDelegate().setRequired(required);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setRequiredMessage(String message)
   {
      getDelegate().setRequiredMessage(message);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setRequiredMessage(Callable<String> message)
   {
      getDelegate().setRequiredMessage(message);
      return this;
   }

   @Override
   public Converter<String, VALUETYPE> getValueConverter()
   {
      return getDelegate().getValueConverter();
   }

   @Override
   public UIInputMany<VALUETYPE> setValueConverter(Converter<String, VALUETYPE> converter)
   {
      getDelegate().setValueConverter(converter);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> addValidator(UIValidator validator)
   {
      getDelegate().addValidator(validator);
      return this;
   }

   @Override
   public Iterable<VALUETYPE> getValue()
   {
      return getDelegate().getValue();
   }

   @Override
   public ListenerRegistration<ValueChangeListener> addValueChangeListener(ValueChangeListener listener)
   {
      return getDelegate().addValueChangeListener(listener);
   }

   @Override
   public void validate(UIValidationContext context)
   {
      getDelegate().validate(context);
   }

   @Override
   public UIInputMany<VALUETYPE> setNote(String note)
   {
      getDelegate().setNote(note);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setNote(Callable<String> note)
   {
      getDelegate().setNote(note);
      return this;
   }

   @Override
   public String getNote()
   {
      return getDelegate().getNote();
   }

   @Override
   public boolean install(HintsFacet facet)
   {
      return getDelegate().install(facet);
   }

   @Override
   public boolean register(HintsFacet facet)
   {
      return getDelegate().register(facet);
   }

   @Override
   public boolean unregister(HintsFacet facet)
   {
      return unregister(facet);
   }

   @Override
   public boolean uninstall(HintsFacet facet)
   {
      return getDelegate().uninstall(facet);
   }

   @Override
   public boolean hasFacet(Class<? extends HintsFacet> type)
   {
      return getDelegate().hasFacet(type);
   }

   @Override
   @SuppressWarnings("unchecked")
   public boolean hasAllFacets(Class<? extends HintsFacet>... facetDependencies)
   {
      return getDelegate().hasAllFacets(facetDependencies);
   }

   @Override
   public boolean hasAllFacets(Iterable<Class<? extends HintsFacet>> facetDependencies)
   {
      return getDelegate().hasAllFacets(facetDependencies);
   }

   @Override
   public <F extends HintsFacet> F getFacet(Class<F> type) throws FacetNotFoundException
   {
      return getDelegate().getFacet(type);
   }

   @Override
   public Iterable<HintsFacet> getFacets()
   {
      return getDelegate().getFacets();
   }

   @Override
   public <F extends HintsFacet> Optional<F> getFacetAsOptional(Class<F> type)
   {
      return getDelegate().getFacetAsOptional(type);
   }

   @Override
   public <F extends HintsFacet> Iterable<F> getFacets(Class<F> type)
   {
      return getDelegate().getFacets(type);
   }

   @Override
   public <F extends HintsFacet> boolean supports(F facet)
   {
      return getDelegate().supports(facet);
   }

   @Override
   public UIInputMany<VALUETYPE> setDefaultValue(Iterable<VALUETYPE> value)
   {
      getDelegate().setDefaultValue(value);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setDefaultValue(Callable<Iterable<VALUETYPE>> callback)
   {
      getDelegate().setDefaultValue(callback);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setValue(Iterable<VALUETYPE> value)
   {
      getDelegate().setValue(value);
      return this;
   }

   @Override
   public UICompleter<VALUETYPE> getCompleter()
   {
      return getDelegate().getCompleter();
   }

   @Override
   public UIInputMany<VALUETYPE> setCompleter(UICompleter<VALUETYPE> completer)
   {
      getDelegate().setCompleter(completer);
      return this;
   }

   @Override
   public UIInputMany<VALUETYPE> setDeprecated(boolean deprecated)
   {
      getDelegate().setDeprecated(deprecated);
      return this;
   }

   @Override
   public boolean isDeprecated()
   {
      return getDelegate().isDeprecated();
   }

   @Override
   public UIInputMany<VALUETYPE> setDeprecatedMessage(String message)
   {
      getDelegate().setDeprecatedMessage(message);
      return this;
   }

   @Override
   public String getDeprecatedMessage()
   {
      return getDelegate().getDeprecatedMessage();
   }
}
