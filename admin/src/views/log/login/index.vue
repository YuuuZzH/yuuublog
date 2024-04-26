<template>
    <div class="app-container">
        <el-form :inline="true" label-width="80">
            <el-form-item label="地址">
                <el-input v-model="address" placeholder="请输入地址" clearable size="small"
                    @keyup.enter.native="queryLoginLog" @clear="queryLoginLog" />
            </el-form-item>
            <el-form-item label="用户昵称">
                <el-input v-model="nickname" placeholder="请输入昵称" clearable size="small"
                    @keyup.enter.native="queryLoginLog" @clear="queryLoginLog" />
            </el-form-item>
            <el-form-item label="时间选择">
                <el-switch style="display: block ;top: 9px;" v-model="nearly" active-color="rgb(107, 179, 41)" inactive-color="#13ce66"
                    @change="queryLoginLog" active-text="近三十天" inactive-text="近七天">
                </el-switch>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="queryLoginLog">搜索</el-button>
            </el-form-item>
            <el-form-item>
                <el-button type="danger" plain icon="el-icon-delete" size="mini"
                    :disabled="!$hasPermission('log:loginlog:remove') || multiple" @click="handleDelete">删除</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData" stripe style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="index" label="用户编号" type="index" width="150" align="center">
            </el-table-column>
            <el-table-column prop="nickName" label="用户昵称" align="center">
            </el-table-column>
            <el-table-column prop="browser" label="登录浏览器" align="center">
            </el-table-column>
            <el-table-column prop="devName" label="登录设备" align="center">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" align="center">
            </el-table-column>
            <el-table-column prop="ip" label="登录IP" align="center">
            </el-table-column>
            <el-table-column prop="os" label="登录系统" align="center">
            </el-table-column>
            <el-table-column prop="loginTime" label="最后登录日期" align="center">
            </el-table-column>
            <el-table-column prop="address" label="登录地址" align="center">
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-delete"
                        :disabled="!$hasPermission('content:tag:remove')"
                        @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination :page-size.sync="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"
            :page-sizes="[10, 20, 30, 40]" :current-page.sync="pageNo" @current-change="queryLoginLog"
            @size-change="queryLoginLog" />
    </div>
</template>

<script>
import request from '@/utils/request'
import { delLoginLog } from '@/api/log/loginlog'
import { praseStrEmpty } from '@/utils/format'
export default {
    name: 'LoginLog',
    data() {
        return {
            tableData: [],
            pageNo: 1,
            pageSize: 10,
            total: 0,
            ids: [],
            nearly: 0, // 近一周
            address: '',
            nickname: '',
        }
    },
    methods: {
        queryLoginLog() {
            request.post('/log/loginLog', {
                pageNo: this.pageNo,
                pageSize: this.pageSize,
                nearly: this.nearly,
                address: praseStrEmpty(this.address),
                nickname: praseStrEmpty(this.nickname)
            }).then(res => {
                this.tableData = res.rows
                this.total = res.total
            })
        },
        handleSelectionChange(selection) {
            this.ids = selection.map((item) => item.id)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        handleDelete(row) {
            const ids = row.id || this.ids
            this.$modal
                .confirm('是否确认删除标签编号为"' + ids + '"的数据项？')
                .then(function () {
                    return delLoginLog(ids)
                })
                .then(() => {
                    this.getList()
                    this.$modal.msgSuccess('删除成功')
                })
                .catch(() => { })
        }
    },
    mounted() {
        this.queryLoginLog()
    },
}
</script>
<style>
#app-container {
    color: rgb(107, 179, 41);
    color: coral;
}
</style>