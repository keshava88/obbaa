/*
 * Copyright 2018 Broadband Forum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadband_forum.obbaa.dmyang.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.broadband_forum.obbaa.dmyang.tx.TXTemplate;
import org.broadband_forum.obbaa.dmyang.tx.TxService;


public class DataStoreTransactionInterceptor implements MethodInterceptor {

    private TxService m_txService;

    public DataStoreTransactionInterceptor() {
    }

    public TxService getTxService() {
        return m_txService;
    }

    public void setTxService(TxService txService) {
        m_txService = txService;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws RuntimeException {
        if (m_txService != null) {
            return m_txService.executeWithTx(new TXTemplate<Object>() {
                @Override
                public Object execute() {
                    try {
                        Object result = invocation.proceed();
                        return result;
                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                }
            });
        } else {
            return null;
        }
    }
}
