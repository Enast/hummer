/**
 * 订单管理 交易订单
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
        <el-input size="small" v-model="formInline.machineNo" placeholder="输入任务Id"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.orderNo" placeholder="输入任务名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.transId" placeholder="输入任务编号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input size="small" v-model="formInline.transId" placeholder="输入服务标识"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select size="small" v-model="formInline.payType" placeholder="请选择">
          <el-option v-for="type in statusType" :label="type.key" :value="type.value" :key="type.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="small" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table size="small" :data="listData" highlight-current-row v-loading="loading" border element-loading-text="拼命加载中" style="width: 100%;">
      <el-table-column align="center" type="index" width="60">
      </el-table-column>
      <el-table-column sortable prop="id" label="任务Id" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="name" label="任务名称" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="taskNo" label="任务编号" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="server" label="服务标识" width="140" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="preTaskId" label="前置任务id" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="taskCron" label="corn" width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="retryTimes" label="重试次数" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="retryTimesLimit" label="重试次数上限" width="140" show-overflow-tooltip>
      </el-table-column>
      <el-table-column sortable prop="status" label="任务状态" width="120" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{scope.row.status|taskStatus}}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="addTime" label="最近执行时间" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{scope.row.lastExecuteTime|timestampToTime}}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="created" label="创建时间" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{scope.row.created|timestampToTime}}</div>
        </template>
      </el-table-column>
      <el-table-column sortable prop="modified" label="修改时间" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{scope.row.modified|timestampToTime}}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="150">
        <template slot-scope="scope">
<!--          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">预览</el-button>-->
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">关闭</el-button>
          <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">启动</el-button>
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
            <el-form-item label="公司编号">
              <el-input size="small" v-model="editForm.deptId" auto-complete="off" placeholder="请输入名称" disabled></el-input>
            </el-form-item>
            <el-form-item label="订单号">
              <el-input size="small" v-model="editForm.orderNo" auto-complete="off" placeholder="请输入商户号" disabled></el-input>
            </el-form-item>
            <el-form-item label="支付方式">
              <el-input size="small" v-model="editForm.payType" auto-complete="off" placeholder="请输入商户号" disabled></el-input>
            </el-form-item>
            <el-form-item label="交易类型">
              <el-input size="small" v-model="editForm.transType" auto-complete="off" placeholder="请输入微信子商户" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品编号">
              <el-input size="small" v-model="editForm.goodsNo" auto-complete="off" placeholder="请输入应用ID" disabled></el-input>
            </el-form-item>
            <el-form-item label="支付金额">
              <el-input size="small" v-model="editForm.payAmount" auto-complete="off" placeholder="请输入通知回调" disabled></el-input>
            </el-form-item>
            <el-form-item label="货道号">
              <el-input size="small" v-model="editForm.aisleNo" auto-complete="off" placeholder="请输入加密类型" disabled></el-input>
            </el-form-item>
            <el-form-item label="买家标识">
              <el-input size="small" v-model="editForm.openId" auto-complete="off" placeholder="请输入商户签名密钥" disabled></el-input>
            </el-form-item>
            <el-form-item label="子商户号">
              <el-input size="small" v-model="editForm.subMchId" auto-complete="off" placeholder="请输入支付宝卖家" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司名称">
              <el-input size="small" v-model="editForm.deptName" auto-complete="off" placeholder="请输入名称" disabled></el-input>
            </el-form-item>
            <el-form-item label="交易单号">
              <el-input size="small" v-model="editForm.transId" auto-complete="off" placeholder="请输入商户号" disabled></el-input>
            </el-form-item>
            <el-form-item label="子支付方式">
              <el-input size="small" v-model="editForm.subPayType" auto-complete="off" placeholder="请输入商户号" disabled></el-input>
            </el-form-item>
            <el-form-item label="终端编号">
              <el-input size="small" v-model="editForm.machineNo" auto-complete="off" placeholder="请输入微信子商户" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品价格">
              <el-input size="small" v-model="editForm.goodsPrice" auto-complete="off" placeholder="请输入应用ID" disabled></el-input>
            </el-form-item>
            <el-form-item label="商品名称">
              <el-input size="small" v-model="editForm.goodsName" auto-complete="off" placeholder="请输入通知回调" disabled></el-input>
            </el-form-item>
            <el-form-item label="订单状态">
              <el-input size="small" v-model="editForm.orderStatus" auto-complete="off" placeholder="请输入加密类型" disabled></el-input>
            </el-form-item>
            <el-form-item label="商户号">
              <el-input size="small" v-model="editForm.mchId" auto-complete="off" placeholder="请输入商户签名密钥" disabled></el-input>
            </el-form-item>
            <el-form-item label="编辑用户">
              <el-input size="small" v-model="editForm.editUser" auto-complete="off" placeholder="请输入支付宝卖家" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input size="small" v-model="editForm.remark" auto-complete="off" placeholder="请输入微信证书路径" disabled></el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { TaskList, TaskRunning, TaskSave } from '../../api/task'
import Pagination from '../../components/Pagination'
export default {
  data() {
    return {
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: '预览',
      statusType: [
        { key: '请选择', value: 0 },
        { key: '成功', value: 1 },
        { key: '失败', value: 2 },
        { key: '执行中', value: 3 }
      ],
      editForm: {
        id: '',
        name: '',
        payType: 1,
        partner: '',
        subMchId: '',
        appid: '',
        notifyUrl: '',
        signType: '',
        partnerKey: '',
        sellerUserId: '',
        certPath: '',
        certPassword: '',
        rsaKey: '',
        token: localStorage.getItem('logintoken')
      },
      formInline: {
        pageNo: 1,
        pageSize: 10,
        machineNo: '',
        orderNo: '',
        transId: '',
        payType: 0,
        orderStatus: 0,
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
    },
    //显示编辑界面
    handleEdit: function(index, row) {
      this.editFormVisible = true
      this.editForm = row
    },
    // 编辑、增加页面保存方法
    submitForm(editData) {
      this.$refs[editData].validate(valid => {
        if (valid) {
          ConfigSave(this.editForm)
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
              this.$message.error('支付配置信息保存失败，请稍后再试！')
            })
        } else {
          return false
        }
      })
    },
    //
    deleteUser(index, row) {
      this.$confirm('确定要删除吗?', '信息', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          ConfigDelete(row.deptId)
            .then(res => {
              if (res.success) {
                this.$message({
                  type: 'success',
                  message: '公司已删除!'
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
              this.$message.error('支付配置信息删除失败，请稍后再试！')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
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

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>




