import { post, get } from '../lib/js/axios'

export const getUserInfo = (token) => {
    return get("/user/info",null,{
        token:token
    })
}

