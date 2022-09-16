import {createStore} from 'vuex'
import UserModule from './user'
import PkModule from './pk'
import RecordModule from './record'

export default createStore({
    state: {},
    getters: {},
    mutations: {},
    actions: {},
    modules: {
        user: UserModule,
        pk: PkModule,
        record: RecordModule,
    }
})
