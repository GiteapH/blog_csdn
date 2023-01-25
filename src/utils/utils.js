import { formatDate } from '../lib/js/tools'
import { message } from 'ant-design-vue'
import { post, get } from '../lib/js/axios'
export function parseComments(data) {
    if (data.length != 0) {
        let uids = {}, dislikes = {}
        if (data[0].type == 0)
            uids[data[0].cid] = data[0].goodUid ? [data[0].goodUid] : []
        else
            dislikes[data[0].cid] = data[0].goodUid ? [data[0].goodUid] : []
        delete data[0].goodUid
        delete data[0].goodAid
        let result = [data[0]]
        for (let i = 1; i < data.length; i++) {
            if (data[i].cid == data[i - 1].cid) {
                if (data[i].goodUid) {
                    if (data[i].type == 0)
                        uids[data[i].cid].push(data[i].goodUid)
                    else
                        dislikes[data[i].cid].push(data[i].goodUid)
                }
            } else {
                uids[data[i].cid] = []
                dislikes[data[i].cid] = []
                if (data[i].goodUid) {
                    if (data[i].type == 0)
                        uids[data[i].cid].push(data[i].goodUid)
                    else
                        dislikes[data[i].cid].push(data[i].goodUid)
                }
                delete data[i].goodUid
                delete data[0].goodAid
                result.push(data[i])
            }
        }

        return ({
            comments: result,
            comment_users: uids,
            dislikes: dislikes
        })
    }
    else
        return null
}


export function getComments({ comments, comment_users, dislikes }, self, showDetail = true) {
    let rootComment = new Map()
    let users = new Map()
    for (let comment of comments) {
        users.set(comment.id, [comment.username, comment.head, comment.email, comment.describe, comment.cardimg])
    }
    for (let data of comments) {
        // 为根评论
        let pointsSet = new Set(comment_users[data.cid])
        let dislikesSet = new Set(dislikes[data.cid])
        let comment = {
            cid: data.cid,
            points: pointsSet,
            dislikes: dislikesSet,
            toUid:!showDetail?null:data.toUid,
            head: 'img/userHead/' + data.head,
            author: data.username,
            likes: data.like == null ? 0 : Json.parse(data.like).length,
            comment: data.toUid == null ? data.content : ('@' + users.get(data.toUid)[0] + ": " + data.content),
            date: formatDate(data.date),
            replay: (fromId, content) => {
                if (content == '') {
                    message.warning("评论不能为空")
                    return
                }
                post('/comment/insertComment', {
                    uid: self.upuid,
                    aid: self.upaid,
                    content: content,
                    fromUid: fromId,
                    toUid: data.fromUid,
                    date: formatDate(new Date()),
                    precid: data.toUid ? data.precid : data.cid
                }).then(res => {
                    if (res.resultCode == 200) {
                        self.submitting = false
                        message.success("发表成功")
                    } else {
                        message.error('发送异常，请检查网络')
                        self.submitting = false
                    }
                }).catch(err => {
                    message.error(err)
                    self.submitting = false
                })
            },
            showDetailComments: showDetail == true ? () => {
                get('/comment/getDetailComments', {
                    uid: self.upuid,
                    aid: self.upaid,
                    rootUid: data.toUid,
                    answerUid: data.fromUid,
                    cid:data.precid
                }).then(({ data }) => {
                    if (data.resultCode == 200) {
                        console.log(data.data)
                        message.success("获取成功")
                        self.$refs.comment.ShowDetail = true
                        let detailcomments = parseComments(data.data)
                        let comments = getComments(detailcomments, self, false)
                        console.log(detailcomments)
                        self.$refs.comment.detailcomments = comments
                    } else {
                        message.error('获取失败，请检查网络')
                    }
                }).catch(e => {
                })
            } : null,
            isReplay: false,
            children: [],
            visiblity: false
        }
        try {
            console.log(data)
            if (!data.toUid) {
                //               cid
                rootComment.set(data.cid, comment)
            } else {
                rootComment.get(data.precid).children.push(comment)
            }
        } catch (e) {
            console.log(e)
        }
    }
    let res = []
    for (let values of rootComment.values()) {
        res.push(values)
    }
    res.len = comments.length
    return res
}