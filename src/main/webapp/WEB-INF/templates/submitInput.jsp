<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Complex-Machine Test Configuration</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<div id="testType" class="container">

    <!--Configure test type:-->
    <div class="row mt-2"> <!--Missing Transitions row-->
        <div class="col-xs-4">
            <h4>What are you testing for?</h4>
        </div>
        <div class="col-xs-8">
            <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-warning btn-lg">
                    <input type="radio" class="messageRadio" name="testType" value="missingTransitions"
                           id="missingTransitionsButton" disabled="disabled">Missing Transitions
                </label>
                <div class="row mt-2"><!--Extra Transitions row-->
                </div>
                <label class="btn btn-warning btn-lg">
                    <input type="radio" class="messageRadio" name="testType" value="extraTransitions"
                           id="extraTransitionsButton" disabled="disabled"> Extra Transitions
                </label>
                <div class="row mt-2"><!--Misdirected Transitions row-->
                </div>
                <label class="btn btn-warning btn-lg">
                    <input type="radio" class="messageRadio" name="testType" value="misdirectedTransitions"
                           id="misdirectedTransitionsButton" disabled="disabled"> Misdirected Transitions
                </label>
            </div>
        </div>
    </div>
    <!--Configure test type: Load Specifications-->
    <div id="loadSpec" class="row mt-2">
        <div class="col-xs-4">
            <div class="col-xs-4">
                <h4>Load Specification</h4>
            </div>
        </div>
        <div class="col-xs-8">
            <button class="btn btn-primary btn-lg" onclick="loadSpecificationFile();">Load from Directory
            </button>
            <form id="specificationFileForm" enctype="multipart/form-data">
                <input type="file" id="fileSpecification" name="specificationFile" style="display:none"
                       onchange="uploadSpecifications()">
            </form>
        </div>
    </div>

    <!--Configure test type: Load Java System File-->
    <div id="loadTestSystem" class="row mt-2">
        <div class="col-xs-4">
            <h4>Load Java Test System</h4>
        </div>
        <div class="col-xs-8">
            <button class="btn btn-primary btn-lg" onclick="loadSystemFile();">Load from Directory
            </button>
            <form id="systemFileForm" enctype="multipart/form-data">
                <input type="file" id="fileJavaTestSystem" name="uploadedSystemFile" style="display:none"
                       onchange="uploadSystem()">
            </form>
        </div>
    </div>

    <!--Back button-->
    <div id="backButton" class="row">
        <div class="col-md-6">
            <a class="btn btn-danger" href="/" role="button">Back
            </a>
        </div>
        <div class="col-md-6">
            <button id="startTestButton" disabled="disabled" class="btn btn-large btn-success" onclick="startTest()">
                Start Test
            </button>
        </div>
    </div>
</div>
<!--End-->
</body>
<script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.form.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.redirect.js" />"></script>
<script type="application/javascript">
    var specificationUploadSuccessful = false;
    var systemUploadSuccessful = false;
    var testTypeSuccessful = false;
    var specification;
    function loadSpecificationFile() {
        document.getElementById("fileSpecification").click();
    }

    function uploadSpecifications() {
        if ($("#fileSpecification").val().length > 0) {
            $("#specificationFileForm").ajaxSubmit({
                url: '/uploadSpecifications',
                type: 'post',
                success: function (response) {
                    alert(response.message);
                    if (response.hasOwnProperty("specification")) {
                        specification = response.specification;
                        $("#specificationInput").val(JSON.stringify(specification));
                        specificationUploadSuccessful = true;
                    } else {
                        specificationUploadSuccessful = false;
                    }
                    checkValidForm();
                },
                error: function (response) {
                    alert(response.responseJSON.error);
                    specificationUploadSuccessful = false;
                    checkValidForm();
                }
            });
        }
        return false;
    }

    var system;
    function loadSystemFile() {
        document.getElementById("fileJavaTestSystem").click();
    }

    function uploadSystem() {
        if ($("#fileJavaTestSystem").val().length > 0) {
            $("#systemFileForm").ajaxSubmit({
                url: '/uploadSystem',
                type: 'post',
                success: function (response) {
                    alert(response.message);
                    if (response.hasOwnProperty("result") &&
                            response.result == "true") {
                        systemUploadSuccessful = true;
                    } else {
                        systemUploadSuccessful = false;
                    }
                    checkValidForm();
                },
                error: function (response) {
                    alert(response.responseJSON.error);
                    specificationUploadSuccessful = false
                    checkValidForm();
                }
            });
        }
        return false;
    }

    function checkValidForm() {
        if (testTypeSuccessful && specificationUploadSuccessful && systemUploadSuccessful) {
            $("#startTestButton").prop("disabled", false);
        } else {
            $("#startTestButton").prop("disabled", true);
        }
    }

    function setChecked(component) {
        component.addClass('btn-success');
        component.removeClass('btn-warning');
    }

    function setUnchecked(component) {
        component.addClass('btn-warning');
        component.removeClass('btn-success');
    }

    $(document).ready(function () {
        var missingTransitionsButton = $('#missingTransitionsButton');
        var extraTransitionsButton = $('#extraTransitionsButton');
        var misdirectedTransitionButton = $('#misdirectedTransitionsButton');
        $('input[type="radio"]').on('change', function () {
            if (missingTransitionsButton.is(":checked")) {
                setChecked(missingTransitionsButton.parent());
                setUnchecked(extraTransitionsButton.parent());
                setUnchecked(misdirectedTransitionButton.parent());
            } else if (extraTransitionsButton.is(":checked")) {
                setUnchecked(misdirectedTransitionButton.parent());
                setUnchecked(missingTransitionsButton.parent());
                setChecked(extraTransitionsButton.parent());
            } else if(misdirectedTransitionButton.is(":checked")){
                setUnchecked(extraTransitionsButton.parent());
                setUnchecked(missingTransitionsButton.parent());
                setChecked(misdirectedTransitionButton.parent());
            }
            if ($('input[name="testType"]').is(':checked')) {
                testTypeSuccessful = true;
                checkValidForm();
            }
        });
    });

    function startTest() {
        var testType = $("input[name='testType']:checked").val();
        jQuery.ajax({
            type: "POST",
            url: "/startTest",
            data: {specification: JSON.stringify(specification), testType: testType},
            success: function (response) {
                if (response.hasOwnProperty("result")) {
                    $.redirect('/testResults', {'result': JSON.stringify(response.result)});
                } else {
                    alert(response.message);
                }
            },
            error: function (response) {
                alert(response.responseJSON.error);
            }
        });
        return false;
    }
</script>
</html>
