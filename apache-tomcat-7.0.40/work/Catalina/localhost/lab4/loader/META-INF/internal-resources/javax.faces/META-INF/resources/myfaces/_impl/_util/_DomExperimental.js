if(_MF_SINGLTN){_MF_SINGLTN(_PFX_UTIL+"_DomExperimental",myfaces._impl._util._Dom,{constructor_:function(){this._callSuper("constructor_");myfaces._impl._util._Dom=this;},getWindowId:function(E){var B="form";var H=function(M){var J={};var I;var P=0;for(var O=M.length-1;O>=0;O--){var L="undefined";var N=M[O];var K=N["javax.faces.WindowId"]&&N["javax.faces.WindowId"].value;if(L!=typeof K){if(P>0&&L==typeof J[K]){throw Error("Multiple different windowIds found in document");}I=K;J[K]=true;P++;}}return I;};var D=function(I){if(!I){return document.forms;}var K=[];if(!I.tagName){return[];}else{if(I.tagName.toLowerCase()==B){K.push(I);return K;}}if(I.querySelectorAll){return I.querySelectorAll(B);}for(var J=I.childNodes.length-1;J>=0;J--){var L=I.childNodes[J];K=K.concat(D(L,B));}return K;};var G=function(){var J=window.location.href;var I="windowId";var L=new RegExp("[\\?&]"+I+"=([^&#\\;]*)");var K=L.exec(J);if(K!=null){return K[1];}return null;};var F=(E&&(typeof E=="string"||E instanceof String))?document.getElementById(E):(E||null);var C=D(F);var A=H(C);return(null!=A)?A:G();},html5FormDetection:function(B){var A=this._RT.browser;if(A.isIEMobile&&A.isIEMobile<=8){return null;}var C=this.getAttribute(B,"form");return(C)?this.byId(C):null;},isMultipartCandidate:function(B){if(this._Lang.isString(B)){B=this._Lang.strToArray(B,/\s+/);}for(var F=0,A=B.length;F<A;F++){var E=this.byId(B[F]);var C=this.findByTagName(E,"input",true);for(var G=0,D=C.length;G<D;G++){if(this.getAttribute(C[G],"type")=="file"){return true;}}}return false;}});}