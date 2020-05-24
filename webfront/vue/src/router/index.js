// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 首页
import index from '@/views/index';

import taskList from '@/views/task/TaskList';

import logList from '@/views/log/LogList';

// 启用路由
Vue.use(Router);

// 导出路由
export default new Router({
    routes: [{
        path: '/',
        name: '',
        component: taskList,
        // hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/index',
        name: '首页',
        component: index,
        iconCls: 'el-icon-tickets',
        children: [{
            path: '/log/LogList',
            name: '日志',
            component: logList,
            meta: {
                requireAuth: false
            }
        }, {
          path: '/task/TaskList',
          name: '任务列表',
          component: taskList,
          meta: {
            requireAuth: false
          }
        }]
    }]
})
