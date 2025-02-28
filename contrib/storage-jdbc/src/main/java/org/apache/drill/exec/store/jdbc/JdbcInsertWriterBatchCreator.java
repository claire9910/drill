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
package org.apache.drill.exec.store.jdbc;

import org.apache.drill.common.exceptions.ExecutionSetupException;
import org.apache.drill.exec.ops.ExecutorFragmentContext;
import org.apache.drill.exec.physical.impl.BatchCreator;
import org.apache.drill.exec.physical.impl.InsertWriterRecordBatch;
import org.apache.drill.exec.proto.UserBitShared.UserCredentials;
import org.apache.drill.exec.record.CloseableRecordBatch;
import org.apache.drill.exec.record.RecordBatch;

import javax.sql.DataSource;
import java.util.List;

@SuppressWarnings("unused")
public class JdbcInsertWriterBatchCreator implements BatchCreator<JdbcInsertWriter> {

  @Override
  public CloseableRecordBatch getBatch(ExecutorFragmentContext context,
    JdbcInsertWriter config, List<RecordBatch> children)
    throws ExecutionSetupException {
    assert children != null && children.size() == 1;

    UserCredentials userCreds = context.getContextInformation().getQueryUserCredentials();
    DataSource ds = config.getPlugin().getDataSource(userCreds)
      .orElseThrow(() -> new ExecutionSetupException(String.format(
        "Query user %s could obtain a connection to %s, missing credentials?",
        userCreds.getUserName(),
        config.getPlugin().getName()
      )));

    return new InsertWriterRecordBatch(
      config,
      children.iterator().next(),
      context,
      new JdbcTableModifyWriter(ds, config.getTableIdentifier(), config)
    );
  }
}
