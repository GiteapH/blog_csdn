import {post,put,del,get} from '../../lib/js/axios'
import {ElMessage} from 'element-plus'

const userRequest = {
    getUserInfoByIds(ids,pageSize,page){
        return new Promise(function(resolve, reject){
            get('/user/infoBIs',{
                uids:ids,
                limit:pageSize,
                start:page
            }).then(({data})=>{
                console.log(data)
                resolve(data)
            }).catch(e=>{
                
                reject(e)
            })
        })
    },
    getUser2Up(loginUid, upUid){
        return new Promise(function(resolve, reject){
            get("/user/login2up",{
                upUid:upUid,
                loginUid:loginUid
            }).then(({data})=>{
                console.log(data)
                resolve(data.data)
            }).catch(e=>{
                reject(e.message)
            })
        })
    },
    addFan(fanUid,toUid,type=0){
        return new Promise(function(resolve, reject){
            put("/fan/putFan",{
                type:type,
                uid:fanUid,
                toUid:toUid
            }).then(({data})=>{
                console.log(data)
                resolve(data.data)
            }).catch(e=>{
                reject(e.message)
            })
        })
    },
    delFan(fid){
        if(fid!=null){
            del('/fan/delFan',null,{
                fid:fid
            }).then(({data})=>{
                resolve(data.data)
            }).catch(e=>{
                ElMessage.error(e?.message)
                reject(e.message)
            })
        }else{
            ElMessage.error("关注信息已不存在")
        }
    }
}

export default userRequest;
