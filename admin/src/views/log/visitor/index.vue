<template>
    <div class="app-container">
        <el-form :inline="true" label-width="80">
            <el-form-item label="时间选择">
                <el-switch style="display: block ;top: 9px;" v-model="nearly" active-color="rgb(107, 179, 41)" inactive-color="#13ce66"
                    @change="queryLoginLog" active-text="近三十天" inactive-text="近七天">
                </el-switch>
            </el-form-item>
        </el-form>

        
    </div>
</template>

<script>
import request from '@/utils/request'

export default {
    name: 'VisitLog',
    data() {
        return {
            tableData: [],
            pageNo: 1,
            pageSize: 10,
            total: 0,
            nearly: 0, // 近一周
        }
    },
    methods: {
        queryLoginLog() {
            request.post('/log/visitorLog', {
                pageNo: this.pageNo,
                pageSize: this.pageSize,
                nearly: this.nearly,
            }).then(res => {
                this.tableData = res.rows
                this.total = res.total
            })
        },
    },
    mounted() {
        this.queryLoginLog()
    },
}
</script>