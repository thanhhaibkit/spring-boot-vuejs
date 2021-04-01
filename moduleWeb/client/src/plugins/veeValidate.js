import Vue from "vue";
import {
  ValidationProvider,
  ValidationObserver,
  setInteractionMode,
  extend
} from "vee-validate";
import * as Rules from "vee-validate/dist/rules";
import { messages } from 'vee-validate/dist/locale/ja.json';

setInteractionMode("passive");
Vue.component("ValidationProvider", ValidationProvider);
Vue.component("ValidationObserver", ValidationObserver);

Object.keys(Rules).forEach(rule => {
  extend(rule, {
    ...Rules[rule],
    message: messages[rule]
  });
});