import {createStore} from 'vuex'
import UserModule from './user'
import PKModule from './pk'

export default createStore({
    state: {},
    getters: {},
    mutations: {},
    actions: {},
    modules: {
        user: UserModule,
        pk: PKModule,
    }
})
