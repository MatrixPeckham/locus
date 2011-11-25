<%-- 
    Document   : Registration
    Created on : Nov 23, 2011, 2:20:00 PM
    Author     : William Peckham
--%>

<div id="regformdiv" class="round-corner">
    <form id="registerform" action="" onsubmit="return validateAndSubmitRegistration()">
        <table id="registertable">
            <tr>
                <td class="formlabel"><label><span class="required">*</span>Display Name</label></td>
                <td class="forminput"><input id="name" onchange="nameExists(this.value)" onkeyup="nameExists(this.value)" type="text"></input></td>
                <td class="formerror">
                    <label id="uniquename" class="error">Display Name Already Exists</label>
                    <label id="invalidname" class="error">Not a Valid Display Name</label>
                    <label id="requiredname" class="error">This is a Required Field</label>
                    <label id="testingname" class="error">Testing</label>
                    <label id="servererrname" class="error">Error Contacting Server</label>
                </td>
            </tr>
            <tr>
                <td class="formlabel"><label><span class="required">*</span>Email Address</label></td>
                <td class="forminput"><input id="email" onchange="emailExists(this.value)" onkeyup="emailExists(this.value)" type="text"></input></td>
                <td class="formerror">
                    <label id="unique" class="error">Email Already Exists</label>
                    <label id="invalid" class="error">Not a Valid Email</label>
                    <label id="requiredemail" class="error">This is a Required Field</label>
                    <label id="testing" class="error">Testing</label>
                    <label id="servererr" class="error">Error Contacting Server</label>
                </td>
            </tr>
            <tr>
                <td class="formlabel"><label><span class="required">*</span>Password</label></td>
                <td class="forminput"><input id="pass" type="password"></input></td>
                <td class="formerror">
                    <label id="requiredpass" class="error">This is a Required Field</label>
                    <label id="mustmatch1" class="error">Passwords Are Not Equal</label>
                </td>
            </tr>
            <tr>
                <td class="formlabel"><label><span class="required">*</span>Password Again</label></td>
                <td class="forminput"><input id="pass2" type="password"></input></td>
                <td class="formerror">
                    <label id="requiredpass2" class="error">This is a Required Field</label>
                    <label id="mustmatch2" class="error">Passwords Are Not Equal</label>
                </td>
            </tr>
            <tr>
                <td><span class="smalltext"><span class="required">*</span> indicates a required field</span></td>
                <td><input type="Submit" value="Submit"></input></td>
            </tr>
        </table>
    </form>
</div>