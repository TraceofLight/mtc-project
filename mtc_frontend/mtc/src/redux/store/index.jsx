import { combineReducers, configureStore } from "@reduxjs/toolkit";
import logger from "redux-logger";

import persistReducer from "redux-persist/es/persistReducer";
import persistStore from "redux-persist/es/persistStore";
import sessionStorage from "redux-persist/es/storage/session";

import userStatusSlice from "redux/slice/userStatusSlice";

const persistConfiguration = {
  key: "root",
  storage: sessionStorage,
};

const rootReducer = combineReducers({
  userStatus: userStatusSlice,
});

const persistedReducer = persistReducer(persistConfiguration, rootReducer);

const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({ serializableCheck: false }).concat(logger),
});

export const persistor = persistStore(store);
export default store;
