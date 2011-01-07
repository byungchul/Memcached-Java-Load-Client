/**                                                                                                                                                                                
 * Copyright (c) 2010 Yahoo! Inc. All rights reserved.                                                                                                                             
 *                                                                                                                                                                                 
 * Licensed under the Apache License, Version 2.0 (the "License"); you                                                                                                             
 * may not use this file except in compliance with the License. You                                                                                                                
 * may obtain a copy of the License at                                                                                                                                             
 *                                                                                                                                                                                 
 * http://www.apache.org/licenses/LICENSE-2.0                                                                                                                                      
 *                                                                                                                                                                                 
 * Unless required by applicable law or agreed to in writing, software                                                                                                             
 * distributed under the License is distributed on an "AS IS" BASIS,                                                                                                               
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or                                                                                                                 
 * implied. See the License for the specific language governing                                                                                                                    
 * permissions and limitations under the License. See accompanying                                                                                                                 
 * LICENSE file.                                                                                                                                                                   
 */

package com.yahoo.ycsb.client;

import java.util.Properties;

//import org.apache.log4j.BasicConfigurator;

/**
 * Main class for executing YCSB.
 */
public class Client {
	public static Client client = null;
	public static final String OPERATION_COUNT_PROPERTY = "operationcount";
	public static final String RECORD_COUNT_PROPERTY = "recordcount";
	public static final String WORKLOAD_PROPERTY = "workload";
	public static final String PROTOCOL_PROPERTY = "protocol";
	public static final String INSERT_COUNT_PROPERTY = "insertcount";
	public static final String PRINT_STATS_INTERVAL = "printstatsinterval";
	public static final String PRINT_STATS_INTERVAL_DEFAULT = "5";
	
	int status;
	Properties props;
	
	protected Client(Properties props) {
		this.props = null;
	}
	
	public Client getClient() {
		if (client == null)
			client = new Client(null);
		return client;
	}
}
