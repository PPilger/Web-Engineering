_MF_CLS(_PFX_XHR+"_AjaxRequest",_MF_OBJECT,{_contentType:"application/x-www-form-urlencoded",_source:null,_context:null,_sourceForm:null,_passThrough:null,_timeout:null,_queueSize:-1,_xhrQueue:null,_partialIdsArray:null,_xhr:null,_ajaxType:"POST",ENCODED_URL:"javax.faces.encodedURL",_CONTENT_TYPE:"Content-Type",_HEAD_FACES_REQ:"Faces-Request",_VAL_AJAX:"partial/ajax",_XHR_CONST:myfaces._impl.xhrCore.engine.XhrConst,constructor_:function(A){try{this._callSuper("constructor_",A);this._initDefaultFinalizableFields();delete this._resettableContent["_xhrQueue"];this.applyArgs(A);var B=myfaces._impl.xhrCore;this._AJAXUTIL=B._AjaxUtils;}catch(C){this._stdErrorHandler(this._xhr,this._context,C);}},send:function(){var C=this._Lang;var D=this._RT;try{var G=C.hitch(this,function(K){return C.hitch(this,this[K]);});this._xhr=C.mixMaps(this._getTransport(),{onprogress:G("onprogress"),ontimeout:G("ontimeout"),onloadend:G("ondone"),onload:G("onsuccess"),onerror:G("onerror")},true);var J=this._xhr,A=this._sourceForm,F=(typeof A.elements[this.ENCODED_URL]=="undefined")?A.action:A.elements[this.ENCODED_URL].value,B=this.getFormData();for(var I in this._passThrough){if(!this._passThrough.hasOwnProperty(I)){continue;}B.append(I,this._passThrough[I]);}J.open(this._ajaxType,F+((this._ajaxType=="GET")?"?"+this._formDataToURI(B):""),true);J.timeout=this._timeout||0;var H=this._contentType+"; charset=utf-8";J.setRequestHeader(this._CONTENT_TYPE,H);J.setRequestHeader(this._HEAD_FACES_REQ,this._VAL_AJAX);if(this._RT.browser.isWebKit){J.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");}this._sendEvent("BEGIN");if(B&&B.makeFinal){B=B.makeFinal();}J.send((this._ajaxType!="GET")?B:null);}catch(E){E=(E._mfInternal)?E:this._Lang.makeException(new Error(),"sendError","sendError",this._nameSpace,"send",E.message);this._stdErrorHandler(this._xhr,this._context,E);}},ondone:function(){this._requestDone();},onsuccess:function(){var A=this._context;var C=this._xhr;try{this._sendEvent("COMPLETE");A._mfInternal=A._mfInternal||{};jsf.ajax.response((C.getXHRObject)?C.getXHRObject():C,A);}catch(B){this._stdErrorHandler(this._xhr,this._context,B);}},onerror:function(){var A=this._context;var I=this._xhr;var C=this._Lang;var E="";this._sendEvent("COMPLETE");try{var H=C.getMessage("UNKNOWN");var D=("undefined"!=I.status&&null!=I.status)?I.status:H;var B=("undefined"!=I.statusText&&null!=I.statusText)?I.statusText:H;E=C.getMessage("ERR_REQU_FAILED",null,D,B);}catch(F){E=C.getMessage("ERR_REQ_FAILED_UNKNOWN",null);}finally{var G=this.attr("impl");G.sendError(I,A,G.HTTPERROR,G.HTTPERROR,E,"","myfaces._impl.xhrCore._AjaxRequest","onerror");}},onprogress:function(){},ontimeout:function(){try{this._sendEvent("TIMEOUT_EVENT");}finally{this._requestDone();}},_formDataToURI:function(A){if(A&&A.makeFinal){A=A.makeFinal();}return A;},_getTransport:function(){var A=this._RT.getXHRObject();return new myfaces._impl.xhrCore.engine.Xhr1({xhrObject:A});},getFormData:function(){var B=this._AJAXUTIL,A=this._context.myfaces;return this._Lang.createFormDataDecorator(jsf.getViewState(this._sourceForm));},_stdErrorHandler:function(C,B,A){var D=this._xhrQueue;try{this.attr("impl").stdErrorHandler(C,B,A);}finally{if(D){D.cleanup();}}},_sendEvent:function(B){var A=this.attr("impl");A.sendEvent(this._xhr,this._context,A[B]);},_requestDone:function(){var A=this._xhrQueue;if(A){A.processQueue();}delete this._context.source;this._finalize();},_finalize:function(){if(this._xhr.readyState==this._XHR_CONST.READY_STATE_DONE){this._callSuper("_finalize");}}});