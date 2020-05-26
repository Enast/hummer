/**
 * 基础菜单 商品管理
 */
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">日志</el-breadcrumb-item>
      <el-breadcrumb-item>列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="formInline" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" v-model="formInline.search" placeholder="输入任务名称/服务标识" @input="search"></el-input>
      </el-form-item>
<!--      <el-form-item label="">-->
<!--        <el-input size="small" v-model="formInline.server" placeholder="输入服务标识" @input="search"></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>-->
<!--&lt;!&ndash;        <el-button size="small" type="primary" icon="el-icon-plus" @click="handleEdit()">添加</el-button>&ndash;&gt;-->
<!--      </el-form-item>-->
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">
<!--      <el-table-column align="center" type="selection" width="60">-->
<!--      </el-table-column>-->
      <el-table-column sortable prop="name" label="任务名称" width="150" >
      </el-table-column>
      <el-table-column sortable prop="server" label="服务标识" width="150">
      </el-table-column>
      <el-table-column sortable prop="taskId" label="任务Id" width="300">
      </el-table-column>
      <el-table-column sortable prop="status" label="任务状态" width="150">
        <template slot-scope="scope">
          <div>{{scope.row.status|taskStatus}}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="duration" label="耗时（ms）" width="150">
      </el-table-column>
      <el-table-column sortable prop="taskLog" label="log" width="300">
      </el-table-column>
      <el-table-column sortable prop="created" label="时间" width="300">
        <template slot-scope="scope">
          <div>{{scope.row.created|timestampToTime}}</div>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="操作" min-width="300">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
<!--          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>
    <!-- 分页组件 -->
    <Pagination v-bind:child-msg="pageparm" @callFather="callFather"></Pagination>
    <!-- 编辑界面 -->
  </div>
</template>

<script>
import { LogList } from '../../api/log'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      nshow: true, //switch开启
      fshow: false, //switch关闭
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '添加',
      formInline: {
        page: 1,
        limit: 10,
        varLable: '',
        varName: '',
        search: '',
        token: localStorage.getItem('logintoken')
      },
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
    // 获取公司列表
    getdata(parameter) {
      this.loading = true
      // 模拟数据开始
      let res = {
        code: 0,
        msg: null,
        count: 5,
        data: [
          {
            name: null,
            server: null,
            taskId: 1521062371000,
            created: 1526700200000,
            duration: 2,
            status: 'success',
            taskLog: '1'
          }
        ]
      }
      this.loading = false
      this.listData = res.data
      this.pageparm.currentPage = this.formInline.page
      this.pageparm.pageSize = this.formInline.limit
      this.pageparm.total = res.count
      /***
       * 调用接口，注释上面模拟数据 取消下面注释
       */
      LogList(parameter)
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
        })
    },
    // 分页插件事件
    callFather(parm) {
      this.formInline.page = parm.currentPage
      this.formInline.limit = parm.pageSize
      this.getdata(this.formInline)
    },
    // 搜索事件
    search() {
      this.getdata(this.formInline)
    }
  }
}
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>


