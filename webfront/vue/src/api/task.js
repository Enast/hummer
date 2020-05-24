import {req} from "./axiosFun";
import axios from "axios";

/**
 * 任务管理
 **/
// 任务列表
export const TaskList = (params) => { return req("post", "/api/taskService/v1/task/page", params) };
// 任务更新
export const TaskSave = (params) => { return req("post", "/api/taskService/v1/task/update", params) };
// 启动任务
export const TaskRunning = (params) => { return req("post", "/api/taskService/v1/task/running", params) };
