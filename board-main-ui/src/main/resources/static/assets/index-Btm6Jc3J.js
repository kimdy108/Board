import{s as o}from"./index-BTQgR7ZA.js";import{B as c,o as s,d as a,k as r,m as p}from"./index-hZkCZhQ4.js";var f=function(e){var t=e.dt;return`
.p-iconfield {
    position: relative;
}

.p-inputicon {
    position: absolute;
    top: 50%;
    margin-top: calc(-1 * (`.concat(t("icon.size"),` / 2));
    color: `).concat(t("iconfield.icon.color"),`;
    line-height: 1;
}

.p-iconfield .p-inputicon:first-child {
    left: `).concat(t("form.field.padding.x"),`;
}

.p-iconfield .p-inputicon:last-child {
    right: `).concat(t("form.field.padding.x"),`;
}

.p-iconfield .p-inputtext:not(:first-child) {
    padding-left: calc((`).concat(t("form.field.padding.x")," * 2) + ").concat(t("icon.size"),`);
}

.p-iconfield .p-inputtext:not(:last-child) {
    padding-right: calc((`).concat(t("form.field.padding.x")," * 2) + ").concat(t("icon.size"),`);
}
`)},u={root:"p-iconfield"},m=c.extend({name:"iconfield",theme:f,classes:u}),h={name:"BaseIconField",extends:o,style:m,provide:function(){return{$pcIconField:this,$parentInstance:this}}},$={name:"IconField",extends:h,inheritAttrs:!1};function v(n,e,t,l,d,i){return s(),a("div",p({class:n.cx("root")},n.ptmi("root")),[r(n.$slots,"default")],16)}$.render=v;var I={root:"p-inputicon"},x=c.extend({name:"inputicon",classes:I}),g={name:"BaseInputIcon",extends:o,style:x,props:{class:null},provide:function(){return{$pcInputIcon:this,$parentInstance:this}}},B={name:"InputIcon",extends:g,inheritAttrs:!1,computed:{containerClass:function(){return[this.cx("root"),this.class]}}};function y(n,e,t,l,d,i){return s(),a("span",p({class:i.containerClass},n.ptmi("root")),[r(n.$slots,"default")],16)}B.render=y;export{$ as a,B as s};
