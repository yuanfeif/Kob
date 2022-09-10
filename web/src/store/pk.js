export default {
    state: {
        status: "matching", // matching/playing
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        game_map: null,
    },
    getters: {

    },
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGamemap(state, game_map) {
            state.game_map = game_map;
        }
    },
    actions: {
        
    },
    modules: {
    }
}