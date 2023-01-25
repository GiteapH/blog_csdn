<template>
    <div class="fix"></div>
    <div :style="style">
        <div class="card" v-show="signin && !forget">
            <div class="avatar">
                <a-avatar :size="64" style="margin-top: 55px;" :src="src">
                    <template #icon>
                        <UserOutlined />
                    </template>
                </a-avatar>
                <p
                    style="font-family:Georgia, 'Times New Roman', Times, serif;font-weight:300;font-size:20px;margin-top:25px">
                    {{ username }}</p>
            </div>
            <a-form :model="formStateIn" name="normal_login" class="login-form" @finish="signIn" :rules="inRules">
                <a-form-item label="用户名/邮箱" name="username">
                    <a-input v-model:value="formStateIn.username" @blur="setUsername">
                        <template #prefix>
                            <UserOutlined class="site-form-item-icon" />
                        </template>
                    </a-input>
                </a-form-item>

                <a-form-item label="密码" name="password">
                    <a-input-password v-model:value="formStateIn.password">
                        <template #prefix>
                            <LockOutlined class="site-form-item-icon" />
                        </template>
                    </a-input-password>
                </a-form-item>

                <a-form-item>
                    <a-form-item name="remember" no-style>
                        <a-checkbox v-model:checked="formStateIn.remember">记住密码</a-checkbox>
                    </a-form-item>
                    <a class="login-form-forgot" style="float:right" @click="forgetPassword">忘记密码?</a>
                </a-form-item>

                <a-form-item>
                    <div style="text-align:center">
                        <a-button type="primary" html-type="submit" class="login-form-button" :loading="isChecking"
                            style="margin-right:50px;width: 150px;">
                            登录
                        </a-button>
                        <a @click="toSignUp">还没有账号? 马上注册</a>
                    </div>
                </a-form-item>
            </a-form>
        </div>
        <div class="card" v-show="!signin && !forget">
            <div class="avatar">
                <p style="font-family:'Courier New', Courier, monospace;font-weight:300;font-size:30px;margin-top:85px">
                    欢迎加入</p>
            </div>
            <a-form :model="formStateUp" name="normal_login" class="login-form" @finish="signUp" :rules="upRules">
                <a-form-item label="用户名" name="username">
                    <a-input v-model:value="formStateUp.username">
                        <template #prefix>
                            <UserOutlined class="site-form-item-icon" />
                        </template>
                    </a-input>
                </a-form-item>
                <a-form-item label="注册邮箱" name="mail">
                    <a-input v-model:value="formStateUp.mail">
                        <template #prefix>
                            <MailOutlined class="site-form-item-icon" />
                        </template>
                    </a-input>
                </a-form-item>

                <a-form-item label="密码" name="password">
                    <a-input-password v-model:value="formStateUp.password">
                        <template #prefix>
                            <LockOutlined class="site-form-item-icon" />
                        </template>
                    </a-input-password>
                </a-form-item>
                <a-form-item label="确认密码" name="reconfirmPassword">
                    <a-input-password v-model:value="formStateUp.reconfirmPassword">
                        <template #prefix>
                            <LockOutlined class="site-form-item-icon" />
                        </template>
                    </a-input-password>
                </a-form-item>
                <a-form-item label="邮箱验证码" name="code">
                    <a-input v-model:value="formStateUp.code">
                        <template #prefix>
                            <a-button type="link" id="getCode" @click="getCode(formStateUp.mail)" :disabled="onReadSec">
                                {{ Code }}
                            </a-button>
                        </template>
                    </a-input>
                </a-form-item>
                <a-form-item>
                    <div style="text-align:center">
                        <div style="margin-left:100px">
                            <a-button :disabled="disabled" type="primary" html-type="submit" class="login-form-button"
                                :loading="isRegedit" style="width: 150px;">
                                注册
                            </a-button>
                            <a-button type="link" @click="toSignIn">已有账号?点此登录</a-button>
                        </div>
                    </div>
                </a-form-item>
            </a-form>
        </div>
    </div>
    <div class="card" v-show="forget">
        <div class="avatar">
            <p style="font-family:'Courier New', Courier, monospace;font-weight:300;font-size:30px;margin-top:85px">
                找回密码</p>
        </div>
        <a-form :model="formStateForget" name="normal_login" class="login-form" @finish="updatePass"
            :rules="forgetRules">
            <a-form-item label="邮箱" name="mail">
                <a-input v-model:value="formStateForget.mail">
                    <template #prefix>
                        <MailOutlined class="site-form-item-icon" />
                    </template>
                </a-input>
            </a-form-item>

            <a-form-item label="密码" name="password">
                <a-input-password v-model:value="formStateForget.password">
                    <template #prefix>
                        <LockOutlined class="site-form-item-icon" />
                    </template>
                </a-input-password>
            </a-form-item>

            <a-form-item label="确认密码" name="reconfirmPassword">
                <a-input-password v-model:value="formStateForget.reconfirmPassword">
                    <template #prefix>
                        <LockOutlined class="site-form-item-icon" />
                    </template>
                </a-input-password>
            </a-form-item>
            <a-form-item label="邮箱验证码" name="code">
                <a-input v-model:value="formStateForget.code">
                    <template #prefix>
                        <a-button type="link" id="getCode" @click="getCode(formStateForget.mail)" :disabled="onReadSec">
                            {{ Code }}
                        </a-button>
                    </template>
                </a-input>
            </a-form-item>
            <a-form-item>
                <div style="text-align:center">
                    <div>
                        <a-button type="primary" style="width:150px" html-type="submit">修改密码</a-button>
                        <a-button type="link" id="getCode" @click="returnSignin">返回登录
                        </a-button>
                    </div>
                </div>
            </a-form-item>
        </a-form>
    </div>
</template>
<script>
import { defineComponent } from 'vue';
import { UserOutlined, LockOutlined, MailOutlined } from '@ant-design/icons-vue';
import { post,put } from '../../lib/js/axios'
import { message } from 'ant-design-vue';
import cookie from 'js-cookie'
import md5 from 'js-md5'
import $ from 'jquery'
export default defineComponent({
    data() {
        return {
            username: '',
            isChecking: false,
            src: '',
            isRegedit: false,
            style: {
                'background': 'url(' + require('@/assets/img/swirl_pattern.webp') + ') repeat center center',
                'height': '1085px',

            },
            forget: false,
            Code: '点击获取验证码',
            checkCode: '',
            onReadSec: false,
            formStateIn: {
                username: '',
                password: '',
                remember: true,
            },
            formStateUp: {
                username: '',
                password: '',
                mail: '',
                code: '',
                reconfirmPassword: ''
            },
            formStateForget: {
                password: '',
                mail: '',
                code: '',
                reconfirmPassword: ''
            },
            signin: true,
            inRules: {
                username: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        console.log(rule)
                        let username = this.formStateIn.username
                        if (username.length == 0) {
                            return Promise.reject('用户名/邮箱不能为空')

                        }
                        else return Promise.resolve()
                    }
                }],
                password: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let password = this.formStateIn.password
                        console.log(password)
                        if (password.length == 0) {
                            return Promise.reject('密码不能为空')

                        }
                        else return Promise.resolve()
                    }
                }]
            },
            upRules: {
                username: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        console.log(rule)
                        let username = this.formStateUp.username
                        console.log(username)
                        if (username.length == 0) {
                            return Promise.reject('用户名/邮箱不能为空')

                        }
                        else if (username.length < 5) {
                            return Promise.reject('用户名/邮箱不能少于5字数');
                        }
                        else if (username.length > 20) {
                            return Promise.reject('用户名/邮箱不能多于20字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                reconfirmPassword: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let password = this.formStateUp.reconfirmPassword
                        console.log(password + "   " + this.formStateUp.password)
                        if (password != this.formStateUp.password) {
                            return Promise.reject('两次输入密码不同!')

                        }
                        else return Promise.resolve()
                    }
                }],
                password: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let rgx = /^(?![a-zA-Z]+$)(?!\d+$)(?![^\da-zA-Z\s]+$).{8,30}$/
                        let password = this.formStateUp.password
                        if (password.length == 0) {
                            return Promise.reject('密码不能为空')

                        }
                        if (!rgx.test(password)) {
                            return Promise.reject('由字母、数字、特殊字符,任意2种组成')
                        }
                        else if (password.length < 8) {
                            return Promise.reject('密码不能少于8字数');
                        }
                        else if (password.length > 30) {
                            return Promise.reject('密码不能多于30字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                mail: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let ex = /[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}/

                        let mail = this.formStateUp.mail
                        console.log(mail)
                        if (mail.length == 0) {
                            return Promise.reject('邮箱不能为空')

                        }
                        if (!ex.test(mail)) {
                            return Promise.reject('邮箱格式错误')
                        }
                        else if (mail.length < 6) {
                            return Promise.reject('邮箱不能少于6字数');
                        }
                        else if (mail.length > 25) {
                            return Promise.reject('邮箱不能多于25字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                code: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let code = this.formStateUp.code
                        if (this.checkCode == '') {
                            message.warn("请先获取验证码!")
                            return
                        }
                        if (code.length == 0) {
                            return Promise.reject('验证码不能为空')

                        }
                        else if (code != this.checkCode) {
                            return Promise.reject('验证码错误');
                        }
                        else return Promise.resolve()
                    }
                }]
            },
            forgetRules: {
                username: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        console.log(rule)
                        let username = this.formStateForget.username
                        console.log(username)
                        if (username.length == 0) {
                            return Promise.reject('用户名/邮箱不能为空')

                        }
                        else if (username.length < 5) {
                            return Promise.reject('用户名/邮箱不能少于5字数');
                        }
                        else if (username.length > 20) {
                            return Promise.reject('用户名/邮箱不能多于20字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                reconfirmPassword: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let password = this.formStateForget.reconfirmPassword
                        console.log(password + "   " + this.formStateForget.password)
                        if (password != this.formStateForget.password) {
                            return Promise.reject('两次输入密码不同!')

                        }
                        else return Promise.resolve()
                    }
                }],
                password: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let rgx = /^(?![a-zA-Z]+$)(?!\d+$)(?![^\da-zA-Z\s]+$).{8,30}$/
                        let password = this.formStateForget.password
                        if (password.length == 0) {
                            return Promise.reject('密码不能为空')

                        }
                        if (!rgx.test(password)) {
                            return Promise.reject('由字母、数字、特殊字符,任意2种组成')
                        }
                        else if (password.length < 8) {
                            return Promise.reject('密码不能少于8字数');
                        }
                        else if (password.length > 30) {
                            return Promise.reject('密码不能多于30字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                mail: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let ex = /[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}/

                        let mail = this.formStateForget.mail
                        console.log(mail)
                        if (mail.length == 0) {
                            return Promise.reject('邮箱不能为空')

                        }
                        if (!ex.test(mail)) {
                            return Promise.reject('邮箱格式错误')
                        }
                        else if (mail.length < 6) {
                            return Promise.reject('邮箱不能少于6字数');
                        }
                        else if (mail.length > 25) {
                            return Promise.reject('邮箱不能多于25字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                code: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let code = this.formStateForget.code
                        if (this.checkCode == '') {
                            message.warn("请先获取验证码!")
                            return
                        }
                        if (code.length == 0) {
                            return Promise.reject('验证码不能为空')

                        }
                        else if (code != this.checkCode) {
                            return Promise.reject('验证码错误');
                        }
                        else return Promise.resolve()
                    }
                }]
            }
        };
    },
    mounted() {
        let url = window.location.search.substring(1).split('&')
        let mail = cookie.get('/user/email')
        let password = cookie.get('/user/password')
        if (mail) {
            this.formStateIn.username = mail
        }
        if (password) {
            this.formStateIn.password = password
            this.formStateIn.remember = true
        } else {
            this.formStateIn.remember = false
        }

        if (url[1] != 'in') {
            this.signin = false
        }

    },
    methods: {
        setUsername() {
            post('/user/checkContain', {
                loginName: this.formStateIn.username
            }).then((user) => {
                if (user) {
                    this.username = user.data.username
                }
            })
        },
        // 登录去往忘记密码
        forgetPassword() {
            this.forget = true;
        },
        //忘记密码返回登录
        returnSignin() {
            this.forget = false;
        },
        // 发送验证码
        getCode(mail) {
            let ex = /[a-zA-Z0-9]+([-_.][A-Za-zd]+)*@([a-zA-Z0-9]+[-.])+[A-Za-zd]{2,5}/
            let bool = true
            if (mail.length == 0) {
                bool = false
                return message.warning('邮箱不能为空')

            }
            if (!ex.test(mail)) {
                bool = false
                return message.warning('邮箱格式错误')
            }
            else if (mail.length < 6) {
                bool = false
                return message.warning('邮箱不能少于6字数');
            }
            else if (mail.length > 25) {
                bool = false
                return message.warning('邮箱不能多于25字数');
            }
            if (bool) {
                post('/user/sendMail', {
                    toMail: mail,
                    action: "注册账号"
                }).then((res) => {
                    if (res.resultCode == 200) {
                        message.success('验证码发送成功')
                        this.checkCode = res.data.code
                    }
                })
                let time = 59
                let inter = setInterval(() => {
                    if (time == 0) {
                        this.onReadSec = false
                        this.Code = '点击获取验证码'
                        time = 59
                        clearInterval(inter)
                    } else {
                        this.onReadSec = true
                        this.Code = time + "s后可获取验证码"
                        time--
                    }
                }, 1000)
            }
        },
        // 修改密码点击
        updatePass() {
            post('/user/checkContain', {
                loginName: this.formStateForget.mail,
            }).then((user) => {
                if (user.resultCode != 200) {
                    message.error("不存在该邮箱账号,请重新输入")
                } else {
                    put('/user/info', {
                        email: this.formStateForget.mail,
                        passwordMd5: md5(this.formStateForget.password)
                    }).then((res) => {
                        if (res.resultCode==200) {
                            message.success("修改成功!")
                        } else {
                            message.destroy(res.message)
                        }
                    })
                }
            })
        },
        // 登录点击
        signIn(values) {
            this.isChecking = true
            post('/user/checkContain', {
                loginName: this.formStateIn.username
            }).then((user) => {
                if (user.resultCode == 200) {
                    console.log(user, "username")
                    post('/user/login', {
                        passwordMd5: md5(this.formStateIn.password),
                        loginName: this.formStateIn.username
                    }).then((res) => {
                        if (res.resultCode == 200) {
                            let head = user.data.head
                            message.success("登录成功")
                            // 设置头像
                            this.src = '/img/userHead/' + head
                            cookie.set('/user/email', this.formStateIn.username)
                            cookie.set('/user/loginPass', this.formStateIn.password)
                            if (this.formStateIn.remember) {
                                cookie.set('/user/password', this.formStateIn.password)
                            }
                            else {
                                cookie.remove('/user/password')
                            }
                            cookie.set('/user/uid',res.data.info.id )
                            cookie.set('/user/token', res.data.token)

                            // 跳转link
                            this.$router.go(-1)

                        } else {
                            message.error("您输入的密码有误")
                        }
                        this.isChecking = false
                    }).catch(err => {
                        message.error('服务器连接失败')
                        this.isChecking = false
                    })
                } else {
                    message.error("您输入的邮箱不存在")
                    this.isChecking = false
                }
            }).catch(err => {
                message.error('服务器连接失败')
                this.isChecking = false
            })
            console.log('Success:', values);
        },
        // 注册点击
        signUp() {
            this.isRegedit = true
            let data = {
                loginName: this.formStateUp.username,
                password: md5(this.formStateUp.password),
                email: this.formStateUp.mail,
                head: 'logo.png',
                cardImg: 'cardImg.png',
                describe: '前往个人空间编辑你个签名吧'
            }
            post('/user/register', data).then(res => {
                if (res.resultCode == 200) {
                    message.success('注册成功')
                    this.toSignIn()
                } else {
                    message.error(res.message)
                }
            })
            this.isRegedit = false
        },
        // 返回注册
        toSignUp() {
            console.log(41541)
            this.signin = false
        },
        // 返回登录
        toSignIn() {
            this.signin = true
        },
    },
    computed() {
        return !(formStateUp.username && formStateUp.password);
    },
    components: {
        UserOutlined,
        LockOutlined,
        MailOutlined
    }
});
</script>
<style scoped>
input {
    cursor: pointer;
}

#getCode {
    position: absolute;
    right: 0px;
    z-index: 5;
    height: 25px;
    line-height: 17.5px;
}

#getCode:hover {
    color: cornflowerblue;
}

#getCode:hover {
    color: lightseagreen;
}

#components-form-demo-normal-login .login-form {
    max-width: 300px;
}

#components-form-demo-normal-login .login-form-forgot {
    float: right;
}

#components-form-demo-normal-login .login-form-button {
    width: 100%;
}

.card {
    border: solid 1px gainsboro;
    /* Font & Text */
    font-family: Lato, "Helvetica Neue", Arial, Helvetica, sans-serif;
    font-size: 14px;
    font-weight: 400;
    letter-spacing: normal;
    text-align: start;
    vertical-align: baseline;
    /* Color & Background */
    color: rgb(76, 73, 72);
    /* Box */
    background-color: #fff;
    border-radius: 6px;
    padding: 20px;
    width: 500px;
    height: 560px;
    position: relative;
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
    margin-top: 50px;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
    /* Positioning */
}

.fix {
    position: fixed;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
}

.avatar {
    text-align: center;
    padding: 15px;
    margin-bottom: 20px;
    background: url('../../assets/img/signin.jpg');
}
</style>
