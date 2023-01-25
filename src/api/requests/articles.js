import {post,put,del,get} from '../../lib/js/axios'

const article = {
    getTags:()=>{
        return new Promise((resolve, reject) =>{
            get("/article/getTags").then(({data})=>{
                if(data.resultCode==200)
                    resolve(data.data)
                else
                    reject(data.message)
            }).catch(e=>{
                reject(e)
            })
        })
    },
    getCurDaysArticles:(page,limit,sortType,tag)=>{
        return new Promise((resolve, reject)=>{
            get("/article/getAroundCurDay",{
                page:page,
                limit:limit,
                orderPath:sortType,
                tag:tag
            }).then(({data})=>{
                if(data.resultCode==200){
                    resolve(data.data)
                }else{
                    reject(data.message)
                }
            }).catch(e=>{
                reject(e)
            })
        })
    }
}

export default article