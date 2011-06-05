<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Add Hello World Response" otherwise="/login.htm" redirect="/module/feedback/addResponse.form" />
	
<%@ include file="/WEB-INF/template/header.jsp" %>

<br/>

<h3><spring:message code="feedback.predefinedsubjects"/></h3>
<table border="0">
    <tr>
        <td width="100" ><h4>Index</h4></td>
        <td width="200"><h4>Predefined Subject</h4></td>
    </tr>

    <tr>
        <td>${predefinedsubjects.feedbackPredefinedSubjectId} </td>
        <td>${predefinedsubjects.subject} </td>
    </tr>
</table> 
<br/>
${status}
<br/>
<form method="get">
<INPUT TYPE=hidden NAME=predefinedsubjectid VALUE= ${predefinedsubjects.feedbackPredefinedSubjectId} >
<INPUT TYPE=hidden NAME=delete VALUE="1">
<input type="submit" value="Delete" />
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>