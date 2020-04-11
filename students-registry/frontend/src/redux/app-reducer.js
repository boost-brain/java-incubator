const INITIALZED_SUCCESS = "INITIALZED_SUCCESS";
/**
 * Стоит ли вообще делать?
 * @type {{isInitialized: boolean}}
 */

const InitialState = {
    isInitialized: false,
};

const appReducer = (state = InitialState, action) => {
    switch (action.type) {
        case INITIALZED_SUCCESS:
            return {
                ...state,
                isInitialized: true
            };

        default:
            return state;
    }
};

export const initialSuccess = ()=> ({type :INITIALZED_SUCCESS});

export const initializeApp=(dispatch)=>{
// let result=dispatch(getAuthUserData());

};


