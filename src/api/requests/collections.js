import { post, put, del, get } from '../../lib/js/axios'

const collectReq = {
    getAllColByUid(uid) {
        return new Promise((resovle, reject) => {
            get('/goodCollection/getAllByUid', {
                uid: uid
            }).then(({ data }) => {
                if (data.resultCode == 200) {
                    resovle(data.data)
                } else {
                    resovle(data.message)
                }
            }).catch(e => {
                reject(e)
            })
        })
    }
}

export default collectReq