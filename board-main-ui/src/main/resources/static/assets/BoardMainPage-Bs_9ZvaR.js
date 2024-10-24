import{g as P,s as T}from"./index-CHNytka8.js";import{B as w,g as M,a as k,b as g,c as v,i as $,o as f,d as m,F as E,r as A,e as I,f as _,m as S,h as b,j as R,k as G,l,w as a,u,n as z}from"./index-MLJOWxPO.js";import{s as U}from"./index-D48ubAZB.js";var B=function(e){var t=e.dt;return`
.p-splitter {
    display: flex;
    flex-wrap: nowrap;
    border: 1px solid `.concat(t("splitter.border.color"),`;
    background: `).concat(t("splitter.background"),`;
    border-radius: `).concat(t("border.radius.md"),`;
    color: `).concat(t("splitter.color"),`;
}

.p-splitter-vertical {
    flex-direction: column;
}

.p-splitter-gutter {
    flex-grow: 0;
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1;
    background: `).concat(t("splitter.gutter.background"),`;
}

.p-splitter-gutter-handle {
    border-radius: `).concat(t("splitter.handle.border.radius"),`;
    background: `).concat(t("splitter.handle.background"),`;
    transition: outline-color `).concat(t("splitter.transition.duration"),", box-shadow ").concat(t("splitter.transition.duration"),`;
    outline-color: transparent;
}

.p-splitter-gutter-handle:focus-visible {
    box-shadow: `).concat(t("splitter.handle.focus.ring.shadow"),`;
    outline: `).concat(t("splitter.handle.focus.ring.width")," ").concat(t("splitter.handle.focus.ring.style")," ").concat(t("splitter.handle.focus.ring.color"),`;
    outline-offset: `).concat(t("splitter.handle.focus.ring.offset"),`;
}

.p-splitter-horizontal.p-splitter-resizing {
    cursor: col-resize;
    user-select: none;
}

.p-splitter-vertical.p-splitter-resizing {
    cursor: row-resize;
    user-select: none;
}

.p-splitter-horizontal > .p-splitter-gutter > .p-splitter-gutter-handle {
    height: `).concat(t("splitter.handle.size"),`;
    width: 100%;
}

.p-splitter-vertical > .p-splitter-gutter > .p-splitter-gutter-handle {
    width: `).concat(t("splitter.handle.size"),`;
    height: 100%;
}

.p-splitter-horizontal > .p-splitter-gutter {
    cursor: col-resize;
}

.p-splitter-vertical > .p-splitter-gutter {
    cursor: row-resize;
}

.p-splitterpanel {
    flex-grow: 1;
    overflow: hidden;
}

.p-splitterpanel-nested {
    display: flex;
}

.p-splitterpanel .p-splitter {
    flex-grow: 1;
    border: 0 none;
}
`)},D={root:function(e){var t=e.props;return["p-splitter p-component","p-splitter-"+t.layout]},gutter:"p-splitter-gutter",gutterHandle:"p-splitter-gutter-handle"},K={root:function(e){var t=e.props;return[{display:"flex","flex-wrap":"nowrap"},t.layout==="vertical"?{"flex-direction":"column"}:""]}},N=w.extend({name:"splitter",theme:B,classes:D,inlineStyles:K}),O={name:"BaseSplitter",extends:T,props:{layout:{type:String,default:"horizontal"},gutterSize:{type:Number,default:4},stateKey:{type:String,default:null},stateStorage:{type:String,default:"session"},step:{type:Number,default:5}},style:N,provide:function(){return{$pcSplitter:this,$parentInstance:this}}};function L(n){return V(n)||H(n)||F(n)||j()}function j(){throw new TypeError(`Invalid attempt to spread non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`)}function F(n,e){if(n){if(typeof n=="string")return x(n,e);var t={}.toString.call(n).slice(8,-1);return t==="Object"&&n.constructor&&(t=n.constructor.name),t==="Map"||t==="Set"?Array.from(n):t==="Arguments"||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(t)?x(n,e):void 0}}function H(n){if(typeof Symbol<"u"&&n[Symbol.iterator]!=null||n["@@iterator"]!=null)return Array.from(n)}function V(n){if(Array.isArray(n))return x(n)}function x(n,e){(e==null||e>n.length)&&(e=n.length);for(var t=0,i=Array(e);t<e;t++)i[t]=n[t];return i}var y={name:"Splitter",extends:O,inheritAttrs:!1,emits:["resizestart","resizeend","resize"],dragging:!1,mouseMoveListener:null,mouseUpListener:null,touchMoveListener:null,touchEndListener:null,size:null,gutterElement:null,startPos:null,prevPanelElement:null,nextPanelElement:null,nextPanelSize:null,prevPanelSize:null,panelSizes:null,prevPanelIndex:null,timer:null,data:function(){return{prevSize:null}},mounted:function(){var e=this;if(this.panels&&this.panels.length){var t=!1;if(this.isStateful()&&(t=this.restoreState()),!t){var i=L(this.$el.children).filter(function(r){return r.getAttribute("data-pc-name")==="splitterpanel"}),s=[];this.panels.map(function(r,o){var h=r.props&&r.props.size?r.props.size:null,c=h||100/e.panels.length;s[o]=c,i[o].style.flexBasis="calc("+c+"% - "+(e.panels.length-1)*e.gutterSize+"px)"}),this.panelSizes=s,this.prevSize=parseFloat(s[0]).toFixed(4)}}},beforeUnmount:function(){this.clear(),this.unbindMouseListeners()},methods:{isSplitterPanel:function(e){return e.type.name==="SplitterPanel"},onResizeStart:function(e,t,i){this.gutterElement=e.currentTarget||e.target.parentElement,this.size=this.horizontal?M(this.$el):k(this.$el),i||(this.dragging=!0,this.startPos=this.layout==="horizontal"?e.pageX||e.changedTouches[0].pageX:e.pageY||e.changedTouches[0].pageY),this.prevPanelElement=this.gutterElement.previousElementSibling,this.nextPanelElement=this.gutterElement.nextElementSibling,i?(this.prevPanelSize=this.horizontal?g(this.prevPanelElement,!0):v(this.prevPanelElement,!0),this.nextPanelSize=this.horizontal?g(this.nextPanelElement,!0):v(this.nextPanelElement,!0)):(this.prevPanelSize=100*(this.horizontal?g(this.prevPanelElement,!0):v(this.prevPanelElement,!0))/this.size,this.nextPanelSize=100*(this.horizontal?g(this.nextPanelElement,!0):v(this.nextPanelElement,!0))/this.size),this.prevPanelIndex=t,this.$emit("resizestart",{originalEvent:e,sizes:this.panelSizes}),this.$refs.gutter[t].setAttribute("data-p-gutter-resizing",!0),this.$el.setAttribute("data-p-resizing",!0)},onResize:function(e,t,i){var s,r,o;i?this.horizontal?(r=100*(this.prevPanelSize+t)/this.size,o=100*(this.nextPanelSize-t)/this.size):(r=100*(this.prevPanelSize-t)/this.size,o=100*(this.nextPanelSize+t)/this.size):(this.horizontal?s=e.pageX*100/this.size-this.startPos*100/this.size:s=e.pageY*100/this.size-this.startPos*100/this.size,r=this.prevPanelSize+s,o=this.nextPanelSize-s),this.validateResize(r,o)&&(this.prevPanelElement.style.flexBasis="calc("+r+"% - "+(this.panels.length-1)*this.gutterSize+"px)",this.nextPanelElement.style.flexBasis="calc("+o+"% - "+(this.panels.length-1)*this.gutterSize+"px)",this.panelSizes[this.prevPanelIndex]=r,this.panelSizes[this.prevPanelIndex+1]=o,this.prevSize=parseFloat(r).toFixed(4)),this.$emit("resize",{originalEvent:e,sizes:this.panelSizes})},onResizeEnd:function(e){this.isStateful()&&this.saveState(),this.$emit("resizeend",{originalEvent:e,sizes:this.panelSizes}),this.$refs.gutter.forEach(function(t){return t.setAttribute("data-p-gutter-resizing",!1)}),this.$el.setAttribute("data-p-resizing",!1),this.clear()},repeat:function(e,t,i){this.onResizeStart(e,t,!0),this.onResize(e,i,!0)},setTimer:function(e,t,i){var s=this;this.timer||(this.timer=setInterval(function(){s.repeat(e,t,i)},40))},clearTimer:function(){this.timer&&(clearInterval(this.timer),this.timer=null)},onGutterKeyUp:function(){this.clearTimer(),this.onResizeEnd()},onGutterKeyDown:function(e,t){switch(e.code){case"ArrowLeft":{this.layout==="horizontal"&&this.setTimer(e,t,this.step*-1),e.preventDefault();break}case"ArrowRight":{this.layout==="horizontal"&&this.setTimer(e,t,this.step),e.preventDefault();break}case"ArrowDown":{this.layout==="vertical"&&this.setTimer(e,t,this.step*-1),e.preventDefault();break}case"ArrowUp":{this.layout==="vertical"&&this.setTimer(e,t,this.step),e.preventDefault();break}}},onGutterMouseDown:function(e,t){this.onResizeStart(e,t),this.bindMouseListeners()},onGutterTouchStart:function(e,t){this.onResizeStart(e,t),this.bindTouchListeners(),e.preventDefault()},onGutterTouchMove:function(e){this.onResize(e),e.preventDefault()},onGutterTouchEnd:function(e){this.onResizeEnd(e),this.unbindTouchListeners(),e.preventDefault()},bindMouseListeners:function(){var e=this;this.mouseMoveListener||(this.mouseMoveListener=function(t){return e.onResize(t)},document.addEventListener("mousemove",this.mouseMoveListener)),this.mouseUpListener||(this.mouseUpListener=function(t){e.onResizeEnd(t),e.unbindMouseListeners()},document.addEventListener("mouseup",this.mouseUpListener))},bindTouchListeners:function(){var e=this;this.touchMoveListener||(this.touchMoveListener=function(t){return e.onResize(t.changedTouches[0])},document.addEventListener("touchmove",this.touchMoveListener)),this.touchEndListener||(this.touchEndListener=function(t){e.resizeEnd(t),e.unbindTouchListeners()},document.addEventListener("touchend",this.touchEndListener))},validateResize:function(e,t){if(e>100||e<0||t>100||t<0)return!1;var i=P(this.panels[this.prevPanelIndex],"minSize");if(this.panels[this.prevPanelIndex].props&&i&&i>e)return!1;var s=P(this.panels[this.prevPanelIndex+1],"minSize");return!(this.panels[this.prevPanelIndex+1].props&&s&&s>t)},unbindMouseListeners:function(){this.mouseMoveListener&&(document.removeEventListener("mousemove",this.mouseMoveListener),this.mouseMoveListener=null),this.mouseUpListener&&(document.removeEventListener("mouseup",this.mouseUpListener),this.mouseUpListener=null)},unbindTouchListeners:function(){this.touchMoveListener&&(document.removeEventListener("touchmove",this.touchMoveListener),this.touchMoveListener=null),this.touchEndListener&&(document.removeEventListener("touchend",this.touchEndListener),this.touchEndListener=null)},clear:function(){this.dragging=!1,this.size=null,this.startPos=null,this.prevPanelElement=null,this.nextPanelElement=null,this.prevPanelSize=null,this.nextPanelSize=null,this.gutterElement=null,this.prevPanelIndex=null},isStateful:function(){return this.stateKey!=null},getStorage:function(){switch(this.stateStorage){case"local":return window.localStorage;case"session":return window.sessionStorage;default:throw new Error(this.stateStorage+' is not a valid value for the state storage, supported values are "local" and "session".')}},saveState:function(){$(this.panelSizes)&&this.getStorage().setItem(this.stateKey,JSON.stringify(this.panelSizes))},restoreState:function(){var e=this,t=this.getStorage(),i=t.getItem(this.stateKey);if(i){this.panelSizes=JSON.parse(i);var s=L(this.$el.children).filter(function(r){return r.getAttribute("data-pc-name")==="splitterpanel"});return s.forEach(function(r,o){r.style.flexBasis="calc("+e.panelSizes[o]+"% - "+(e.panels.length-1)*e.gutterSize+"px)"}),!0}return!1}},computed:{panels:function(){var e=this,t=[];return this.$slots.default().forEach(function(i){e.isSplitterPanel(i)?t.push(i):i.children instanceof Array&&i.children.forEach(function(s){e.isSplitterPanel(s)&&t.push(s)})}),t},gutterStyle:function(){return this.horizontal?{width:this.gutterSize+"px"}:{height:this.gutterSize+"px"}},horizontal:function(){return this.layout==="horizontal"},getPTOptions:function(){var e;return{context:{nested:(e=this.$parentInstance)===null||e===void 0?void 0:e.nestedState}}}}},C=["onMousedown","onTouchstart","onTouchmove","onTouchend"],W=["aria-orientation","aria-valuenow","onKeydown"];function X(n,e,t,i,s,r){return f(),m("div",S({class:n.cx("root"),style:n.sx("root"),"data-p-resizing":!1},n.ptmi("root",r.getPTOptions)),[(f(!0),m(E,null,A(r.panels,function(o,h){return f(),m(E,{key:h},[(f(),I(_(o),{tabindex:"-1"})),h!==r.panels.length-1?(f(),m("div",S({key:0,ref_for:!0,ref:"gutter",class:n.cx("gutter"),role:"separator",tabindex:"-1",onMousedown:function(p){return r.onGutterMouseDown(p,h)},onTouchstart:function(p){return r.onGutterTouchStart(p,h)},onTouchmove:function(p){return r.onGutterTouchMove(p,h)},onTouchend:function(p){return r.onGutterTouchEnd(p,h)},"data-p-gutter-resizing":!1},n.ptm("gutter")),[b("div",S({class:n.cx("gutterHandle"),tabindex:"0",style:[r.gutterStyle],"aria-orientation":n.layout,"aria-valuenow":s.prevSize,onKeyup:e[0]||(e[0]=function(){return r.onGutterKeyUp&&r.onGutterKeyUp.apply(r,arguments)}),onKeydown:function(p){return r.onGutterKeyDown(p,h)},ref_for:!0},n.ptm("gutterHandle")),null,16,W)],16,C)):R("",!0)],64)}),128))],16)}y.render=X;var Y={root:function(e){var t=e.instance;return["p-splitterpanel",{"p-splitterpanel-nested":t.isNested}]}},J=w.extend({name:"splitterpanel",classes:Y}),q={name:"BaseSplitterPanel",extends:T,props:{size:{type:Number,default:null},minSize:{type:Number,default:null}},style:J,provide:function(){return{$pcSplitterPanel:this,$parentInstance:this}}},d={name:"SplitterPanel",extends:q,inheritAttrs:!1,data:function(){return{nestedState:null}},computed:{isNested:function(){var e=this;return this.$slots.default().some(function(t){return e.nestedState=t.type.name==="Splitter"?!0:null,e.nestedState})},getPTOptions:function(){return{context:{nested:this.isNested}}}}};function Q(n,e,t,i,s,r){return f(),m("div",S({ref:"container",class:n.cx("root")},n.ptmi("root",r.getPTOptions)),[G(n.$slots,"default")],16)}d.render=Q;const Z={class:"mt-2 ml-2 mr-2 mb-3"},ee={class:"card"},ie={__name:"BoardMainPage",setup(n){return(e,t)=>(f(),m("div",Z,[l(u(U),{style:{"background-color":"#007cb2"}},{title:a(()=>t[0]||(t[0]=[b("div",{class:"h-16 text-5xl text-left ml-5"},"Board",-1)])),content:a(()=>[b("div",ee,[l(u(y),{style:{height:"680px"},class:"mb-5"},{default:a(()=>[l(u(d),{size:50},{default:a(()=>[l(u(y),{layout:"vertical"},{default:a(()=>[l(u(d),{class:"flex items-center justify-center border-none text-black",style:{"background-color":"#fffff1"}},{default:a(()=>t[1]||(t[1]=[z(" 공지사항 ")])),_:1}),l(u(d),{class:"flex items-center justify-center border-none text-black",style:{"background-color":"#fffff1"}},{default:a(()=>t[2]||(t[2]=[z(" 개발/스택 ")])),_:1})]),_:1})]),_:1}),l(u(d),{size:50},{default:a(()=>[l(u(y),{layout:"vertical"},{default:a(()=>[l(u(d),{class:"flex items-center justify-center border-none text-black",style:{"background-color":"#fffff1"}},{default:a(()=>t[3]||(t[3]=[z(" 자유게시판 ")])),_:1}),l(u(d),{class:"flex items-center justify-center border-none text-black",style:{"background-color":"#fffff1"}},{default:a(()=>t[4]||(t[4]=[z(" qna ")])),_:1})]),_:1})]),_:1})]),_:1})])]),_:1})]))}};export{ie as default};
