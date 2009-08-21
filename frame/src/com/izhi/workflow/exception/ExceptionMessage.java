/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the LGPL license, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author daquanda(liyingquan@gmail.com)
 * @author kevin(diamond_china@msn.com)
 */
package com.izhi.workflow.exception;

public interface ExceptionMessage {
	public static final String ERROR_FLOWMETAFILE_NULL = "error.flowmetafile.null";
	public static final String ERROR_FLOWMETAFILE_DIGESTER = "error.flowmetafile.digester";
	public static final String ERROR_FLOWMETAFILE_NO_METAS = "error.flowmetafile.nometas";
	public static final String ERROR_FLOWMETAFILE_MULTI_METAS = "error.flowmetafile.multimetas";

	public static final String ERROR_FLOWMETA_DUPLICATE_PROCESS_ID = "error.flowmeta.duplicate_process_id";

	public static final String ERROR_FLOWPROC_UN_MATCHED_OUTPUT_PARAM_VALUE = "error.flowproc.un_matched_output_param_value";

	public static final String ERROR_FLOWDEPLOY_DRIVER_REUSEE = "error.flowdeploy.driver_reuse";
	public static final String ERROR_FLOWDEPLOY_WITH_PROCS = "error.flowdeploy.withprocs";
	public static final String ERROR_FLOWDEPLOY_READY = "error.flowdeploy.ready";

	public static final String ERROR_FLOWTASK_NOT_TASKOWNER = "error.flowtask.nottaskowner";
	public static final String ERROR_FLOWTASK_INVALID_STATE = "error.flowtask.invalidstate";

	public static final String ERROR_FLOWTASK_ABORT_INVALID_STATE = "error.flowtask.abort.invalidstate";

	public static final String ERROR_FLOWENGINE_DIDNOT_DRIVE_ANY_FLOW = "error.flowengine.didnot_drive_any_flow";
}
