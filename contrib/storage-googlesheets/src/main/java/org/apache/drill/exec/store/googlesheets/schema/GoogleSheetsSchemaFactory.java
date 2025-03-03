/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.drill.exec.store.googlesheets.schema;

import org.apache.calcite.schema.SchemaPlus;
import org.apache.drill.exec.store.AbstractSchemaFactory;
import org.apache.drill.exec.store.SchemaConfig;
import org.apache.drill.exec.store.googlesheets.GoogleSheetsStoragePlugin;
import org.apache.drill.exec.store.googlesheets.GoogleSheetsStoragePluginConfig;

public class GoogleSheetsSchemaFactory extends AbstractSchemaFactory {
  private final GoogleSheetsStoragePlugin plugin;

  public GoogleSheetsSchemaFactory(GoogleSheetsStoragePlugin plugin) {
    super(GoogleSheetsStoragePluginConfig.NAME);
    this.plugin = plugin;
  }

  @Override
  public void registerSchemas(SchemaConfig schemaConfig, SchemaPlus parent) {
    GoogleSheetsRootSchema schema = new GoogleSheetsRootSchema(plugin, schemaConfig);
    parent.add(getName(), schema);
  }
}
