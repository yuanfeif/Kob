<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%;">My Bot</span>
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#add-bot-modal">Create</button>

                        <!-- add Modal -->
                        <div class="modal fade" id="add-bot-modal" tabindex="-1">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Create a Bot</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">Name</label>
                                            <input v-model="bot_added.title" type="text" class="form-control"
                                                id="add-bot-title" placeholder="Please input the name of your bot...">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-description" class="form-label">Description</label>
                                            <textarea v-model="bot_added.description" class="form-control"
                                                id="add-bot-description" rows="3"
                                                placeholder="Please input the description of your bot..."></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-code" class="form-label">Code</label>
                                            <VAceEditor v-model:value="bot_added.content" @init="editorInit"
                                                lang="c_cpp" theme="textmate" style="height: 300px" />
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error-message">{{ bot_added.error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="add_bot">Create</button>
                                        <button type="button" class="btn btn-secondary" 
                                            data-bs-dismiss="modal" @click="cancel">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Create Time</th>
                                    <th>Operations</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createtime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-success" style="margin-right: 10px;"
                                            data-bs-toggle="modal"
                                            :data-bs-target="'#update-bot-modal-' + bot.id">Modify</button>

                                        <!-- modify Modal -->
                                        <div class="modal fade" :id="'update-bot-modal-' + bot.id" tabindex="-1">
                                            <div class="modal-dialog modal-xl">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Create a Bot</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">Name</label>
                                                            <input v-model="bot.title" type="text" class="form-control"
                                                                id="add-bot-title"
                                                                placeholder="Please input the name of your bot...">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-description"
                                                                class="form-label">Description</label>
                                                            <textarea v-model="bot.description" class="form-control"
                                                                id="add-bot-description" rows="3"
                                                                placeholder="Please input the description of your bot..."></textarea>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-code" class="form-label">Code</label>
                                                            <VAceEditor v-model:value="bot.content" @init="editorInit"
                                                                lang="c_cpp" theme="textmate" style="height: 300px" />
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ bot.error_message }}</div>
                                                        <button type="button" class="btn btn-primary"
                                                            @click="update_bot(bot)">Modify and Save</button>
                                                        <button type="button" class="btn btn-secondary" 
                                                            data-bs-dismiss="modal" @click="cancel">Cancel</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <button type="button" class="btn btn-danger"
                                            @click="remove_bot(bot)">Delete</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>>
    </div>
</template>

<script>
import { ref, reactive } from 'vue';
import $ from 'jquery';
import { useStore } from 'vuex';
import { Modal } from 'bootstrap/dist/js/bootstrap';
import { VAceEditor } from 'vue3-ace-editor';
import ace from 'ace-builds';

export default {

    components: {
        VAceEditor,
    },

    setup() {

        ace.config.set(
            "basePath",
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")

        const store = useStore();
        let bots = ref([]);

        const bot_added = reactive({
            title: "",
            description: "",
            content: "",
            error_message: "",
        });

        const refresh_bots = () => {
            $.ajax({
                url: "https://app3460.acapp.acwing.com.cn/api/user/bot/select/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp;
                },
            })
        }

        refresh_bots();

        const add_bot = () => {
            bot_added.error_message = "";
                $.ajax({
                    url: "https://app3460.acapp.acwing.com.cn/api/user/bot/add/",
                    type: "post",
                    data: {
                        title: bot_added.title,
                        description: bot_added.description,
                        content: bot_added.content,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        if (resp.error_message === "success") {
                            bot_added.title = "";
                            bot_added.description = "";
                            bot_added.content = "";
                            Modal.getInstance("#add-bot-modal").hide();
                            refresh_bots();
                        } else {
                            bot_added.error_message = resp.error_message;
                        }
                    },
                })
        }

        const update_bot = (bot) => {
            bot_added.error_message = "",
                $.ajax({
                    url: "https://app3460.acapp.acwing.com.cn/api/user/bot/update/",
                    type: "post",
                    data: {
                        bot_id: bot.id,
                        title: bot.title,
                        description: bot.description,
                        content: bot.content,
                    },
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        if (resp.error_message === "success") {
                            Modal.getInstance("#update-bot-modal-" + bot.id).hide();
                            refresh_bots();
                        } else {
                            bot.error_message = resp.error_message;
                        }
                    },
                })
        }

        const remove_bot = (bot) => {
            $.ajax({
                url: "https://app3460.acapp.acwing.com.cn/api/user/bot/remove/",
                type: "post",
                data: {
                    bot_id: bot.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_bots();
                    }
                },
            })
        }

        const cancel = () => {
            bot_added.title = "";
            bot_added.description = "";
            bot_added.content = "";
            refresh_bots();
        }

        return {
            bots,
            bot_added,
            add_bot,
            update_bot,
            remove_bot,
            cancel,
        }
    }
}
</script>

<style scoped>
div.error-message {
    color: red;
}
</style>