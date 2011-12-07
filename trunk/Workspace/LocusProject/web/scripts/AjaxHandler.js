/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function getRequest(){
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}

function changeAd(){
    var xmlhttp=getRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("AdvertiseDiv").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET","JSPChunks/Ad.jsp",true);
    xmlhttp.send();
}

function changePage(page){
    var xmlhttp=getRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("content").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET",page,true);
    xmlhttp.send();
    changeAd();
}

function post(page, params){
    var xmlhttp=getRequest();
    xmlhttp.open("POST",page,false);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send(params);
    return xmlhttp.response;
}

function emailExists(str){
    var xmlhttp=getRequest();
    document.getElementById("testing").style.display = 'block';
    xmlhttp.onreadystatechange=function()
    {
        document.getElementById("testing").style.display = 'none';
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            var mess = xmlhttp.responseText;
            //document.getElementById("content").innerHTML=mess;
            if(mess=="true")
                document.getElementById("unique").style.display = 'block';
            else
                document.getElementById("unique").style.display = 'none';
        } 
    }
    xmlhttp.open("POST","./testEmail.htm",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("email="+encodeURI(str));
}

function nameExists(str){
    var xmlhttp=getRequest();
    document.getElementById("testingname").style.display = 'block';
    xmlhttp.onreadystatechange=function()
    {
        document.getElementById("testingname").style.display = 'none';
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            var mess = xmlhttp.responseText;
            //document.getElementById("content").innerHTML=mess;
            if(mess=="true")
                document.getElementById("uniquename").style.display = 'block';
            else
                document.getElementById("uniquename").style.display = 'none';
        } 
    }
    xmlhttp.open("POST","./testName.htm",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("name="+encodeURI(str));
}

function validateAndSubmitRegistration(){
    var valid = true;
    var email=document.getElementById("email").value;
    var atpos=email.indexOf("@");
    var dotpos=email.lastIndexOf(".");
    document.getElementById("requiredemail").style.display='none';
    document.getElementById("invalid").style.display='none';
    document.getElementById("unique").style.display='none';
    document.getElementById("requiredname").style.display='none';
    document.getElementById("uniquename").style.display='none';
    document.getElementById("requiredpass").style.display='none';
    document.getElementById("requiredpass2").style.display='none';
    document.getElementById("mustmatch1").style.display='none';
    document.getElementById("mustmatch2").style.display='none';
    document.getElementById("servererrname").style.display='none';
    if (email==null||email==""){
        document.getElementById("requiredemail").style.display='block';
        valid=false;
    }
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
        document.getElementById("invalid").style.display = 'block';
        valid=false;
    }
    var emailreq=getRequest();
    emailreq.open("POST","./testEmail.htm",false);
    emailreq.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    emailreq.send("email="+encodeURI(email));
    if(emailreq.responseText=="true"){
        document.getElementById("uniqueemail").style.display = 'block';
        valid=false;
    }
    var name=document.getElementById("name").value;
    if(name==""||name==null){
        document.getElementById("requiredname").style.display='block';
        valid=false;
    }
    var namereq=getRequest();
    namereq.open("POST","./testName.htm",false);
    namereq.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    namereq.send("name="+encodeURI(name));
    if(namereq.responseText=="true"){
        document.getElementById("uniquename").style.display = 'block';
        valid=false;
    }
    var pass1=document.getElementById("pass").value;
    var pass2=document.getElementById("pass2").value;
    if(pass1==null||pass1==""){
        document.getElementById("requiredpass").style.display='block';
        valid=false;
    }
    if(pass2==null||pass2==""){
        document.getElementById("requiredpass2").style.display='block';
        valid=false;
    }
    if(pass1!=pass2){
        document.getElementById("mustmatch1").style.display='block';
        document.getElementById("mustmatch2").style.display='block';
        valid=false;
    }
    if(valid==false){
        return false;
    }
    var addreq = getRequest();
    addreq.open("POST","./RegisterUser.htm", false);
    addreq.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    addreq.send("name="+encodeURI(name) + "&" +
        "email="+encodeURI(email) + "&" +
        "pass="+encodeURI(pass1));
    if(addreq.responseText!="OK"){
        document.getElementById("servererrname").style.display='block';
        document.getElementById("servererrname").innerHTML=addreq.responseText;
        return false;
    }
    
    changePage('JSPChunks/Home.jsp');
    
    return true;
}

function login(){
    var name=document.getElementById("logname").value;
    var pass=document.getElementById("logpass").value;
    var xmlhttp = getRequest();
    xmlhttp.open("POST","./Login.htm",false);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("username="+encodeURI(name)+"&password="+encodeURI(pass));
    var resp=xmlhttp.responseText;
    if(resp.indexOf("Welcome")==0){
        document.getElementById("loginerr").style.display='none';
        document.getElementById("login").innerHTML=resp;
    } else {
        document.getElementById("loginerr").style.display='block';
        document.getElementById("loginerr").innerHTML=resp;
        
    }
    location.reload(true);
}

function logout(){
    var xmlhttp=getRequest();
    var loginText = '<form id="login" action="javascript:void(0)" onsubmit="login()">'+
    '<span><label>Display Name:</label><input type="text" id="logname"></input></span>'+
    '<span><label>Password:</label><input type="password" id="logpass"></input></span>'+
    '<span><input type="submit" id="loggo" value="Go"></input></span>'+
    '<br />'+
    '<span><label class="error" id="loginerr"></label></span>'+
    '<br/>Not a Member? Click <a onclick="changePage(\'JSPChunks/Registration.jsp\')" href="#">here</a> to register.'+
    '</form>';
    xmlhttp.open("GET","./Logout.htm",false)
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("login").innerHTML=loginText;
        } 
    }
    xmlhttp.send(null);
    location.reload(true);
}

function addCard(){
    var numCards = parseInt(document.getElementById("numCards").value);
    var tabConts = document.getElementById("cardtable").innerHTML;
    var newrow = '<tr><td>New Credit Card Number #'+(numCards+1)+'</td><td><input type="hidden" value="-1" id="cardacc'+numCards+'"/><input type="text" id="cardnum'+numCards+'" value=""/></td></tr>';
    numCards=numCards+1;
    document.getElementById("numCards").value=numCards;
    document.getElementById("cardtable").innerHTML=tabConts+newrow;
}

function val(id){
    if(document.getElementById(id)!=null){
        return document.getElementById(id).value;
    }
    return "";
}

function saveProfile(usr){
    var params = "./EditUserInfo.htm?fname="+val("fname")+'&';
    params=params+'lname='+val("lname")+'&';
    params=params+'addr='+val("addr")+'&';
    params=params+'city='+val("city")+'&';
    params=params+'state='+val("state")+'&';
    params=params+'zip='+val("zip")+'&';
    params=params+'preferences='+val("preferences")+'&';
    params=params+'phone='+val("phone");
    var numcards = parseInt(val("numCards"));
    params=params+"&numCards="+numcards;
    for(var i=0;i<=numcards;i++){
        params=params+"&cardacc"+i+"="+val("cardacc"+i);
        params=params+"&cardnum"+i+"="+val("cardnum"+i);
    }
    var editreq=getRequest();
    editreq.open("GET",params,false);
    editreq.send(null);
    changePage("JSPChunks/Profile.jsp?user="+usr);
}
function incNumCards(){
    document.getElementById("numCards").value=(parseInt(document.getElementById("numCards").value)+1);
}

function likePost(user, post, circle){
    var req = getRequest();
    var url = './LikePost.htm?user='+user+'&post='+post;
    req.open("GET",url,false);
    req.send(null);
    changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
}

function unlikePost(user, post, circle){
    var req = getRequest();
    var url = './UnlikePost.htm?user='+user+'&post='+post;
    req.open("GET",url,false);
    req.send(null);
    changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
}

function likeComment(user, comment, circle){
    var req = getRequest();
    var url = './LikeComment.htm?user='+user+'&comment='+comment;
    req.open("GET",url,false);
    req.send(null);
    changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
}

function unlikeComment(user, comment, circle){
    var req = getRequest();
    var url = './UnlikeComment.htm?user='+user+'&comment='+comment;
    req.open("GET",url,false);
    req.send(null);
    changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
}

function showMessage(index){
    var numMessages = parseInt(document.getElementById("nummessages").value);
    for(var i=0;i<numMessages;i++){
        document.getElementById("messagecontent"+i).style.display='none';
    }
    document.getElementById("messagecontent"+index).style.display='block';
}

function sendMessage(){
    var msgcontent = document.getElementById("msgcontent").value;
    var subject = document.getElementById("subject").value;
    var recipient = document.getElementById("recipient").value;
    var params = "recipient="+recipient+"&subject="+subject+"&msgcontent="+msgcontent;
    post("./SendMessage.htm",params);
    changePage("JSPChunks/ViewMessages.jsp");
}

function delMessage(msgToDelete){
    var params = "messageToDelete=" + msgToDelete;
    post("./DelMsg.htm",params);
    changePage("JSPChunks/ViewMessages.jsp");
}

function targetNameValid(str){
    var xmlhttp=getRequest();
    document.getElementById("lookupname").style.display = 'block';
    xmlhttp.onreadystatechange=function()
    {
        document.getElementById("lookupname").style.display = 'none';
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            var mess = xmlhttp.responseText;
            //document.getElementById("content").innerHTML=mess;
            if(mess=="true")
                document.getElementById("usernotfound").style.display = 'none';
            else
                document.getElementById("usernotfound").style.display = 'block';
        } 
    }
    xmlhttp.open("POST","./testName.htm",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("name="+encodeURI(str));
}

function addPost(circle){
    var content = document.getElementById("newpost").value;
    var params="circle="+circle+"&content="+content;
    post("./AddPost.htm",params);
    changePage("JSPChunks/ViewCircle.jsp?circle="+circle);
}

function removePost(postid, circle){
    var params="post="+postid;
    post("./RemovePost.htm",params);
    changePage("JSPChunks/ViewCircle.jsp?circle="+circle);
}

function addComment(postid,circle){
    var content=document.getElementById("newcomment"+postid).value;
    var params="post="+postid+"&content="+content;
    post("./AddComment.htm",params);
    changePage("JSPChunks/ViewCircle.jsp?circle="+circle);
}

function removeComment(comment, circle){
    var params="comment="+comment;
    post("./RemoveComment.htm",params);
    changePage("JSPChunks/ViewCircle.jsp?circle="+circle);
}

function editPost(post){
    document.getElementById("postcontent"+post).style.display='none';
    document.getElementById("postedit"+post).style.display='block';
}

function savePost(postid,circle){
    var content = document.getElementById('posteditcontent'+postid).value;
    var params="content="+content+"&post="+postid;
    post("./UpdatePost.htm",params);
    changePage("JSPChunks/ViewCircle.jsp?circle="+circle);
}

function editComment(comment){
    document.getElementById("commentcontent"+comment).style.display='none';
    document.getElementById("commentedit"+comment).style.display='block';
}

function saveComment(commentid,circle){
    var content = document.getElementById('commenteditcontent'+commentid).value;
    var params="content="+content+"&comment="+commentid;
    post("./UpdateComment.htm",params);
    changePage("JSPChunks/ViewCircle.jsp?circle="+circle);
}

function joinCircle(uid,circle){
    var params='user='+uid+"&circle="+circle;
    var resp = post("./JoinCircle.htm",params);
    if(resp.indexOf('Joined')!=-1){
        changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
        return;
    } else 
    if(resp.indexOf('ERROR')!=-1){
        return;
    } else {
    
        document.getElementById("joinmessage").style.display='block';
    }
}

function acceptMember(uid,circle){
    var params='user='+uid+"&circle="+circle;
    var resp = post("./AcceptMember.htm",params);
}

function declineMember(uid,circle){
    var params='user='+uid+"&circle="+circle;
    var resp = post("./DeclineMember.htm",params);
}

function unjoinCircle(uid,circle){
    var params='user='+uid+"&circle="+circle;
    var resp = post("./UnjoinCircle.htm",params);
    changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
}

function removeMember(uid,circle){
    var params='user='+uid+"&circle="+circle;
    var resp = post("./RemoveUser.htm",params);
    changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
}

function editCircle(){
    document.getElementById("circlehead").style.display='none';
    document.getElementById("editcirclediv").style.display='block';
}

function saveCircle(circle){
    var name=document.getElementById("editcirclename").value;
    var pub=document.getElementById("publiccheck").checked;
    var cat=document.getElementById("editcirclecatagory").value;
    var params = "name="+name+"&pub="+pub+"&circle="+circle+"&catagory="+cat;
    post("./EditCircle.htm",params);
    changePage('JSPChunks/ViewCircle.jsp?circle='+circle);
}

function newCircle(username, userid){
    var name=document.getElementById("newcirclename").value;
    var pub=document.getElementById("newpubliccheck").checked;
    var cat=document.getElementById("newcirclecatagory").value;
    var params = "name="+name+"&pub="+pub+"&catagory="+cat+"&user="+userid;
    post("./NewCircle.htm",params);
    changePage('JSPChunks/Profile.jsp?user='+username);
}

function removeCircle(circle,username){
    var params = "circle="+circle;
    post("./RemoveCircle.htm",params);
    changePage('JSPChunks/Profile.jsp?user='+username);
}

function createEmployee(){
    var link = "./Hire.htm";
    var params = "fname="+val("fname")+'&';
    params=params+'lname='+val("lname")+'&';
    params=params+'pass='+val("pass")+'&';
    params=params+'addr='+val("addr")+'&';
    params=params+'city='+val("city")+'&';
    params=params+'state='+val("state")+'&';
    params=params+'zip='+val("zip")+'&';
    params=params+'preferences='+val("preferences")+'&';
    params=params+'phone='+val("phone")+'&';
    params=params+'email='+val("email")+'&';
    params=params+'username='+val("usrname")+'&';
    params=params+'hourly='+val("hourly");
    post(link,params);
    changePage('JSPChunks/Management.jsp');
}

function editEmployee(){
    var link = "./EditEmployee.htm";
    var params = "fname="+val("fname")+'&';
    params=params+'lname='+val("lname")+'&';
    params=params+'addr='+val("addr")+'&';
    params=params+'city='+val("city")+'&';
    params=params+'state='+val("state")+'&';
    params=params+'zip='+val("zip")+'&';
    params=params+'preferences='+val("preferences")+'&';
    params=params+'phone='+val("phone")+'&';
    params=params+'username='+val("usrname")+'&';
    params=params+'hourly='+val("hourly");
    post(link,params);
    changePage('JSPChunks/Management.jsp');
}

function fireEmployee(empl){
    var params = "user="+empl;
    post("./Fire.htm",params);
    changePage('JSPChunks/Management.jsp');
}

function addAd(){
    var params = "catagory=" + val("newcat");
    params = params + "&company=" + val("newcomp");
    params = params + "&item=" + val("newitem");
    params = params + "&price=" + val("newprice");
    params = params + "&number=" + val("newnum");
    params = params + "&content=" + val("newcont");
    post("./AddAd.htm",params);
    changePage("JSPChunks/EmployeePage.jsp");
}

function deleteAd(adid){
    post("./DeleteAd.htm","adid="+adid);
    changePage("JSPChunks/EmployeePage.jsp");
}

function makePurchase(adid){
    var acct;
    var accounts = document.getElementsByName('account');
    for(var i = 0; i<accounts.length; i++){
        if(accounts.item(i).checked){
            acct=accounts.item(i).value;
        }
    }
    var num = document.getElementById("numItems").value;
    var params="adid=" + adid+"&acct="+acct+"&num="+num;
    var resp = post("./Purchase.htm",params);
    document.getElementById("buyItem").innerHTML=resp;
}

function changeSalesReport(){
    var month = val("monthselect");
    var year = val("yearselect");
    var xmlhttp=getRequest();
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("chosensales").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET","JSPChunks/SalesReport.jsp?month="+month+"&year="+year,true);
    xmlhttp.send();

}
