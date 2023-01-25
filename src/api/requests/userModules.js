import {post,put,del,get} from '../../lib/js/axios'

const UMRequest = {
    getUserModules(uid=-1){
        return new Promise((resolve, reject) => {
            get('/modules/getUMs',{
                uid:uid
            }).then(({data})=>{
                resolve(data.data)
            }).catch(e=>{
                reject(e)
            })
        })
    }
}

export default UMRequest;