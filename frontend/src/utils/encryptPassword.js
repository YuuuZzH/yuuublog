import JSEncrypt from 'jsencrypt';

// 密钥对生成 http://web.chacuo.net/netrsakeypair
const publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCj+WHUA1lj2aC9evQuIe/NeIUTgUvvzcZ5EO+msm60tLMdwTag4reQJ/kVLt+41tgHBTBpoxYDBLRDuGsWCbjXvzlJw1rUEgPI+jHwMkE8FrL2HNY1Opz0H3UV7PKhggGV1f2LEia7O08VLY0BbRXcKm9o93flAKt9O9y48nzTDwIDAQAB"
export default {
    // 加密
    encrypt(txt) {
        const encryptor = new JSEncrypt()
        encryptor.setPublicKey(publicKey) // 设置公钥
        return encryptor.encrypt(txt) // 对需要加密的数据进行加密
    },
    // 解密
    decrypt(txt) {
        const encryptor = new JSEncrypt()
        encryptor.setPrivateKey(privateKey)
        return encryptor.decrypt(txt)
    }
}


