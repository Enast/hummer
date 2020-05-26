import {req} from "./axiosFun";
import axios from "axios";

/**
 * 任务管理
 **/
// 任务列表
export const LogList = (params) => { return req("post", "/api/taskService/v1/taskLog/page", params) };
