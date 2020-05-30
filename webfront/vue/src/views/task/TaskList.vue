/**
* 任务管理
*/
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">任务管理</el-breadcrumb-item>
      <el-breadcrumb-item>任务列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="formInline.taskId" placeholder="输入任务Id"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.name" placeholder="输入任务名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.taskNo" placeholder="输入任务编号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.server" placeholder="输入服务标识"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select size="small" v-model="formInline.status" placeholder="请选择">
          <el-option v-for="type in statusType" :label="type.key" :value="type.value" :key="type.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border
              element-loading-text="拼命加载中" style="width: 100%;">
      <el-table-column align="center" type="index" width="60">
      </el-table-column>
      <el-table-column sortable prop="id" label="任务Id" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="name" label="任务名称" width="140" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="taskNo" label="任务编号" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="server" label="服务标识" width="100" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="preTaskId" label="前置任务id" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="taskCron" label="corn" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="retryTimes" label="重试次数" width="100" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="retryTimesLimit" label="重试上限" width="100" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="status" label="任务状态" width="100" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{scope.row|taskStatus}}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="lastExecuteTime" label="最近执行时间" width="140" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{scope.row.lastExecuteTime|timestampToTime}}</div>
        </template>
      </el-table-column>
      <!--      <el-table-column sortable prop="created" label="创建时间" width="140" show-overflow-tooltip>-->
      <!--        <template slot-scope="scope">-->
      <!--          <div>{{scope.row.created|timestampToTime}}</div>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <!--      <el-table-column sortable prop="modified" label="修改时间" width="140" show-overflow-tooltip>-->
      <!--        <template slot-scope="scope">-->
      <!--          <div>{{scope.row.modified|timestampToTime}}</div>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column align="center" label="操作" min-width="250">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="warning" @click="executeTask(scope.$index, scope.row)">执行</el-button>
          <el-button size="mini" type="danger" @click="closeTask(scope.$index, scope.row)">关闭</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
    <el-dialog :title="title" :visible.sync="editFormVisible" width="50%" @click="closeDialog('editForm')">
      <el-form label-width="120px" :model="editForm" ref="editForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="任务id">
              <el-input size="small" v-model="editForm.id" auto-complete="off" disabled></el-input>
            </el-form-item>
            <el-form-item label="任务名称">
              <el-input size="small" v-model="editForm.name" auto-complete="off" placeholder="请输入任务名称"></el-input>
            </el-form-item>
            <el-form-item label="服务标识">
              <el-input size="small" v-model="editForm.server" auto-complete="off" placeholder="服务标识"
                        disabled></el-input>
            </el-form-item>
            <el-form-item label="任务状态">
              <el-input size="small" v-model="editForm.statusStr" auto-complete="off" placeholder="任务状态"
                        disabled></el-input>
            </el-form-item>
            <el-form-item label="最近执行时间">
              <el-input size="small" v-model="editForm.lastExecuteTimeStr" auto-complete="off" placeholder="最近执行时间"
                        disabled></el-input>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-input size="small" v-model="editForm.createdStr" auto-complete="off" placeholder="修改时间"
                        disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务编号">
              <el-input size="small" v-model="editForm.taskNo" auto-complete="off" disabled></el-input>
            </el-form-item>
            <el-form-item label="cron">
              <el-input size="small" v-model="editForm.taskCron" auto-complete="off" placeholder="cron"></el-input>
            </el-form-item>
            <el-form-item label="重试次数">
              <el-input size="small" v-model="editForm.retryTimes" auto-complete="off" placeholder="重试次数"
                        disabled></el-input>
            </el-form-item>
            <el-form-item label="重试上限">
              <el-input size="small" v-model="editForm.retryTimesLimit" auto-complete="off"
                        placeholder="请输入重试次数上限"></el-input>
            </el-form-item>
            <el-form-item label="修改时间">
              <el-input size="small" v-model="editForm.modifiedStr" auto-complete="off" placeholder="修改时间"
                        disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="前置任务id">
          <el-input size="small" v-model="editForm.preTaskId" auto-complete="off" placeholder="前置任务id"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('editForm')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import {TaskList, TaskRunning, TaskSave} from '../../api/task'
  import Pagination from '../../components/Pagination'
  import {taskStatus, timestampToTime} from "../../utils/util";

  export default {
    data() {
      return {
        loading: false, //是显示加载
        editFormVisible: false, //控制编辑页面显示与隐藏
        title: '编辑',
        statusType: [
          {key: '任务状态', value: 0},
          {key: '成功', value: 3},
          {key: '失败', value: 4},
          {key: '执行中', value: 1},
          {key: '队列中', value: 2}
        ],
        editForm: {
          id: '',
          name: '',
          taskId: null,
          taskNo: '',
          taskCron: '',
          server: '',
          status: '',
          statusStr: '',
          modified: null,
          modifiedStr: null,
          retryTimes: null,
          retryTimesLimit: null,
          preTaskId: null,
          lastExecuteTime: null,
          lastExecuteTimeStr: null,
          token: localStorage.getItem('logintoken')
        },
        formInline: {
          pageNo: 1,
          pageSize: 10,
          taskId: '',
          taskNo: '',
          name: '',
          status: 0,
          server: '',
          token: localStorage.getItem('logintoken')
        },
        // 删除
        seletedata: {
          ids: '',
          token: localStorage.getItem('logintoken')
        },
        userparm: [], //搜索权限
        listData: [], //用户数据
        // 分页参数
        pageparm: {
          currentPage: 1,
          pageSize: 10,
          total: 10
        }
      }
    },
    // 注册组件
    components: {
      Pagination
    },
    /**
     * 数据发生改变
     */

    /**
     * 创建完毕
     */
    created() {
      this.getdata(this.formInline)
    },

    /**
     * 里面的方法只有被调用才会执行
     */
    methods: {
      // 获取任务列表
      getdata(parameter) {
        this.loading = true
        /***
         * 调用接口
         */
        TaskList(parameter)
          .then(res => {
            this.loading = false
            if (res.success == false) {
              this.$message({
                type: 'info',
                message: res.msg
              })
            } else {
              this.listData = res.data.rows
              // 分页赋值
              this.pageparm.currentPage = res.data.pageNo
              this.pageparm.pageSize = res.data.pageSize
              this.pageparm.total = res.data.total
            }
          })
          .catch(err => {
            this.loading = false
            this.$message.error('加载失败，请稍后再试！')
            // this.listData = [{"id": "1", "name": "name", "status": "executing", "dataValid": false}
            //   ,{"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false},
            //   {"id": "1", "name": "name", "status": "executing", "dataValid": false}]
            // this.pageparm.currentPage=1
            // this.pageparm.pageSize=10
            // this.pageparm.total=14
          })
      },
      // 分页插件事件
      callFather(parm) {
        console.log(parm);
        this.formInline.pageNo = parm.currentPage
        this.formInline.pageSize = parm.pageSize
        this.getdata(this.formInline)
      },
      // 搜索事件
      search() {
        this.getdata(this.formInline)
      },
      //显示编辑界面
      handleEdit: function (index, row) {
        this.editFormVisible = true
        this.editForm = row
        this.editForm.statusStr = taskStatus(row)
        this.editForm.createdStr = timestampToTime(row.created)
        this.editForm.lastExecuteTimeStr = timestampToTime(row.lastExecuteTime)
        this.editForm.modifiedStr = timestampToTime(row.modified)
      },
      // 编辑、增加页面保存方法
      submitForm(editData) {
        this.$refs[editData].validate(valid => {
          if (valid) {
            TaskSave(this.editForm)
              .then(res => {
                this.editFormVisible = false
                this.loading = false
                if (res.success) {
                  this.getdata(this.formInline)
                  this.$message({
                    type: 'success',
                    message: '保存成功！'
                  })
                } else {
                  this.$message({
                    type: 'info',
                    message: res.msg
                  })
                }
              })
              .catch(err => {
                this.editFormVisible = false
                this.loading = false
                this.$message.error('信息保存失败，请稍后再试！')
              })
          } else {
            return false
          }
        })
      },
      //
      executeTask(index, row) {
        this.$confirm('确定要执行吗?', '信息', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            TaskRunning(row.id)
              .then(res => {
                if (res.success) {
                  this.$message({
                    type: 'success',
                    message: '操作成功!'
                  })
                  this.getdata(this.formInline)
                } else {
                  this.$message({
                    type: 'info',
                    message: res.msg
                  })
                }
              })
              .catch(err => {
                this.loading = false
                this.$message.error('删除失败，请稍后再试！')
              })
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            })
          })
      },
      closeTask(index, row) {
        this.$confirm('确定要关闭吗?', '信息', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          CloseTask(row.id)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '任务已关闭!'
                })
                this.getdata(this.formInline)
              } else {
                this.$message({
                  type: 'info',
                  message: res.msg
                })
              }
            })
            .catch(err => {
              this.loading = false
              this.$message.error('操作失败，请稍后再试！')
            })
        })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            })
          })
      },
      // 关闭编辑、增加弹出框
      closeDialog(formName) {
        this.editFormVisible = false
        this.$refs[formName].resetFields()
      }
    }
  }
</script>




