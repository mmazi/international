/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.seam.international.status;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import org.jboss.seam.international.status.builder.TemplateMessage;

/**
 * An implementation of the {@link Messages} interface.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 * 
 */
@RequestScoped
public class MessagesImpl implements Messages
{
   private static final long serialVersionUID = -2908193057765795662L;

   private final Set<Message> messages = Collections.synchronizedSet(new HashSet<Message>());
   private final Set<MessageBuilder> builders = Collections.synchronizedSet(new HashSet<MessageBuilder>());

   @Inject
   private MessageFactory factory;

   public void clear()
   {
      messages.clear();
   }

   public boolean isEmpty()
   {
      return messages.isEmpty() && builders.isEmpty();
   }

   public Set<Message> getAll()
   {
      Set<Message> result = new HashSet<Message>();

      synchronized (builders)
      {
         for (MessageBuilder builder : builders)
         {
            messages.add(builder.build());
         }
         builders.clear();
      }

      synchronized (messages)
      {
         result.addAll(messages);
      }
      return result;
   }

   public void add(final Message message)
   {
      messages.add(message);
   }

   public void add(final MessageBuilder builder)
   {
      builders.add(builder);
   }

   private <T extends MessageBuilder> T enqueueBuilder(final T builder)
   {
      add(builder);
      return builder;
   }

   /*
    * Bundle Factory Methods
    */
   public BundleTemplateMessage info(final BundleKey message)
   {
      return enqueueBuilder(factory.info(message));
   }

   public BundleTemplateMessage info(final BundleKey message, final Object... params)
   {
      return enqueueBuilder(factory.info(message, params));
   }

   public BundleTemplateMessage warn(final BundleKey message)
   {
      return enqueueBuilder(factory.warn(message));
   }

   public BundleTemplateMessage warn(final BundleKey message, final Object... params)
   {
      return enqueueBuilder(factory.warn(message, params));
   }

   public BundleTemplateMessage error(final BundleKey message)
   {
      return enqueueBuilder(factory.error(message));
   }

   public BundleTemplateMessage error(final BundleKey message, final Object... params)
   {
      return enqueueBuilder(factory.error(message, params));
   }

   public BundleTemplateMessage fatal(final BundleKey message)
   {
      return enqueueBuilder(factory.fatal(message));
   }

   public BundleTemplateMessage fatal(final BundleKey message, final Object... params)
   {
      return enqueueBuilder(factory.fatal(message, params));
   }

   /*
    * Template Factory Methods
    */
   public TemplateMessage info(final String message)
   {
      return enqueueBuilder(factory.info(message));
   }

   public TemplateMessage info(final String message, final Object... params)
   {
      return enqueueBuilder(factory.info(message, params));
   }

   public TemplateMessage warn(final String message)
   {
      return enqueueBuilder(factory.warn(message));
   }

   public TemplateMessage warn(final String message, final Object... params)
   {
      return enqueueBuilder(factory.warn(message, params));
   }

   public TemplateMessage error(final String message)
   {
      return enqueueBuilder(factory.error(message));
   }

   public TemplateMessage error(final String message, final Object... params)
   {
      return enqueueBuilder(factory.error(message, params));
   }

   public TemplateMessage fatal(final String message)
   {
      return enqueueBuilder(factory.fatal(message));
   }

   public TemplateMessage fatal(final String message, final Object... params)
   {
      return enqueueBuilder(factory.fatal(message, params));
   }

}
