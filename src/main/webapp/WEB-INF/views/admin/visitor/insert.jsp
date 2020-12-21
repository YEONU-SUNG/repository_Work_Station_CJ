<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="/admin/static/js/jquery-ui-1.12.1.js"></script>
<script src="/admin/static/js/Inputmask/dist/jquery.inputmask.min.js"></script>
<link rel="stylesheet" href="/admin/static/css/jquery-ui-1.12.1.css" />
<script src="/admin/static/js/xlsx.full.min.js"></script>
<script>
    $(function() {
		$.datepicker.setDefaults({
			dateFormat : 'yy-mm-dd',
			showOtherMonths : true,
			showMonthAfterYear : true,
			buttonImageOnly : true,
			buttonText : "선택",
			yearSuffix : "년",
			monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8', '9','10', '11', '12' ],
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월','9월', '10월', '11월', '12월' ],
			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ]
		});		

		$('#plan_from_date').datepicker();
		$('#plan_to_date').datepicker();
		
		$('#plan_from_date').datepicker('setDate', 'today');
		$('#plan_to_date').datepicker('setDate', 'today');

        var today = new Date();
        var maxDate = new Date();
        var dd = maxDate.getDate() + 179;
        maxDate.setDate(dd);

        jQuery("#plan_from_date").datepicker("option", "minDate", today);
        jQuery("#plan_to_date").datepicker("option", "minDate", today);
        jQuery("#plan_to_date").datepicker("option", "maxDate", maxDate);
		//$('#visit_from_date').datepicker('setStartDate', new Date());
		//$('#visit_to_date').datepicker('setStartDate', new Date());
        //$('#visit_to_date').datepicker('setDate', '+1D');
    });
    function regenDatepicker(){
    	$(".phone").inputmask({
    		mask : '999-9999-9999',
    		showMaskOnHover : false,
    	});
    	$(".birth").inputmask({
    		mask : '9999-99-99',
    		showMaskOnHover : false,
    	});
    	$(".carNo").inputmask({
    		regex : '([0-9]{2}[가-힣]{1}[0-9]{4}|[가-힣]{2}[0-9]{2}[가-힣]{1}[0-9]{4})',
    		showMaskOnHover : false,
    	});
    }
    
	function setDatePicker() {
		$(document).find("input[name=guest_birth]").removeClass('hasDatepicker').datepicker();
    }

    $(document).on('change', '#plan_from_date', function(dateText) {
        $("#plan_to_date").val(this.value);

        var temp = this.value.split('-');
        var minDate = new Date(temp[0],temp[1]-1,temp[2]);
        var maxDate = new Date(temp[0],temp[1]-1,temp[2]);
        var dd = maxDate.getDate() + 179;
        maxDate.setDate(dd);

        jQuery("#plan_to_date").datepicker("option", "minDate", minDate);
        jQuery("#plan_to_date").datepicker("option", "maxDate", maxDate);
    }); 
</script>
<script>
    
    /**
    * 방문객 객체
    */
    function VisitInfo(id, visitorType) {
        this.id = id;
        this.visitorType = visitorType;
        this.carNo = '';
        this.carray = {
            ware : '노트북',
            serial : '',
            purpose : '협의',
            used : '',
            type : false // 처음로드 시 등록 버튼으로 보이기위해
        };
        this.visitor = {
            type : '',
            name : '',
            birth : '',
            phone : '',
            gender : '',
            location : '',
            company : '',
            dept : ''
        };
        this.addVisitCar = function(carNo) {
            this.carNo = carNo;
        };
        this.addCarray = function(ware, serial, purpose, used) {
            this.carray.ware = ware;
            this.carray.serial = serial;
            this.carray.purpose = purpose;
            this.carray.used = used;
        };
        this.addVisitor = function(type, name, birth, phone, company, dept) {
            this.visitor.type = type;
            this.visitor.name = name;
            this.visitor.birth = birth;
            this.visitor.phone = phone;
            this.visitor.company = company;
            this.visitor.dept = dept;
        };
    };
    
    var module_html = {
        strdepth2 : {},
        //defaultType : 'carray',
        thead : {
            // component : {
            //     carray : function() {
            //         return '반입물품';
            //     },
            //     car : function() {
            //         return '차량정보';
            //     }
            // },
            // tr : function(type) {
            //     // 외부인 방문객 정보 입력 폼
            //     type = type || module_html.defaultType;
            //     return '<tr>' +
            //                 '<th>방문자명</th>' +
            //                 '<th>생년월일</th>' +
            //                 '<th>연락처</th>' +
            //                 // '<th>회사</th>' +
            //                 '<th>'+(type==module_html.defaultType ? module_html.thead.component.carray() : module_html.thead.component.car())+'</th>' +
            //                 '<th>삭제</th>' +
            //             '</tr>';
            // },
            tr : function() {
                // 임직원 방문객 정보 입력 폼
                return '<tr>' +
                            '<th colspan="6">' +
                                '<span style="padding-left: 13px;">방문자 정보</span>' +
                            '</th>' +
                            '<th style="display: none;">반입물품</th>' +
                            '<th>삭제</th>' +
                        '</tr>';
            }
        },
        tbody : {
            component : {
                carray : function(carray) {
                    return  '<input type="hidden" class="nv_input max_100" name="guest_ware" value="'+carray.ware+'" />' +
                            '<input type="hidden" class="nv_input max_100" name="guest_serial" value="'+carray.serial+'" />' +
                            '<input type="hidden" class="nv_input max_100" name="guest_purpose" value="'+carray.purpose+'"/>' +
                            '<input type="hidden" class="nv_input max_100" name="guest_used" value="'+carray.used+'" />' +
                            '<button type="button" class="nv_blue_button nv_modal2_open" name="guest_carry" style="display: none;">'+(carray.type ? '수정' : '등록')+'</button>';
                },
                // car : function(car) {
                //     return  '<input type="checkbox" id="guest_car_type1'+car.id+'" name="guest_car_type" '+(car.carNo=='' && 'checked')+' /><label for="guest_car_type1'+car.id+'">도보</label>' +
                //             '<input type="checkbox" id="guest_car_type2'+car.id+'" name="guest_car_type" '+(car.carNo!='' && 'checked')+' /><label for="guest_car_type2'+car.id+'">차량</label>' +
                //             '<input type="text" class="nv_input max_100" name="guest_car" placeholder="차량번호" style="'+(car.carNo=='' && 'display:none;')+'margin-left: 10px;"; value="'+car.carNo+'" />';
                    
                // }
            },
            // tr : function(type, car, carray, visitor) {
            //     // 외부인 방문자 입력 폼
            //     return '<tr id="group'+car.id+'">' +
            //                 '<td><input type="text" class="nv_input" name="guest_name" placeholder="이름을 입력해주세요." value="'+visitor.name+'" /></td>' +
            //                 '<td><input type="text" placeholder="생년월일을 입력해주세요." class="nv_input birth" name="guest_birth" value="'+visitor.birth+'" /></td>' +
            //                 '<td><input type="text" class="nv_input pone phone" name="guest_phone" placeholder="연락처를 입력해주세요." value="'+visitor.phone+'"/></td>' +
            //                 // '<td><input type="text" class="nv_input" name="guest_company" placeholder="회사명을 입력해주세요." /></td>' +
            //                 '<td>'+ (type==module_html.defaultType ? module_html.tbody.component.carray(carray) : module_html.tbody.component.car(car)) +'</td>' +
            //                 '<td><button type="button" class="nv_red_button" onclick="removeThisRow(this);">삭제</button></td>' +
            //             '</tr>';
            // },
            tr : function(visitInfo) {                
                if(visitInfo.visitorType==1) {
                    // 임직원인경우
                    return '<tr id="group'+visitInfo.id+'">' +
                            '<td colspan="6">' +
                                // '<input type="hidden" id="guest_type'+visitInfo.id+'" name="guest_type" value="'+visitInfo.visitorType+'">' +
                                // '<input type="hidden" id="guest_company'+visitInfo.id+'" name="guest_company" value="'+visitInfo.visitor.company+'">' +
                                // '<input type="hidden" id="guest_dept'+visitInfo.id+'" name="guest_dept" value="'+visitInfo.visitor.dept+'">' +
                                // '<input type="hidden" class="nv_input" id="guest_name'+visitInfo.id+'" name="guest_name'+visitInfo.id+'" value="'+visitInfo.visitor.name+'"/>' +
                                '<input type="text" class="nv_input" id="guest_name'+visitInfo.id+'" name="guest_name" style="float:left; width:90%;" placeholder="검색 할 방문자 입력"/>' +
                                '<button type="button" class="nv_blue_button find_modal">찾기</button>' +
                                '<input type="hidden" id="guest_id'+visitInfo.id+'" name="guest_id" value="'+visitInfo.visitor.id+'">' +
                            '</td>' +
                            '<td style="display: none;">'+(module_html.tbody.component.carray(visitInfo.carray))+'</td>'+
                            '<td><button type="button" class="nv_red_button" onclick="removeThisRow(this);">삭제</button></td>' +
                        '</tr>';
                } else {
                    // 외부인인경우
                    return '<tr id="group'+visitInfo.id+'">' +
                            '<input type="hidden" id="guest_id'+visitInfo.id+'" name="guest_id" value="'+visitInfo.visitor.id+'">' +
                            '<td><input type="text" class="nv_input" name="guest_name" placeholder="이름을 입력해주세요." value="'+visitInfo.visitor.name+'" /></td>' +
                            '<td><input type="text" placeholder="생년월일을 입력해주세요." class="nv_input birth" name="guest_birth" value="'+visitInfo.visitor.birth+'" /></td>' +
                            '<td>' +
                                '<div class="nv_select_box">' +
                                    ((visitInfo.visitor.gender == '') 
                                    ? '<p id="guest_gender'+visitInfo.id+'">성별을 선택해주세요.</p>' 
                                    : '<p id="guest_gender'+visitInfo.id+'">'+visitInfo.visitor.gender+'</p>')+
                                    '<ul>' +
                                        '<li>남</li>' +
                                        '<li>여</li>' +
                                    '</ul>' +
                                '</div>' +
                            '</td>' +
                            '<td>' +
                                '<div class="nv_select_box">' +
                                    ((visitInfo.visitor.location == '') 
                                    ? '<p id="guest_location'+visitInfo.id+'">국적을 선택해주세요.</p>' 
                                    : '<p id="guest_location'+visitInfo.id+'">'+visitInfo.visitor.gender+'</p>')+
                                    '<ul>' +
                                        '<li>Korea South</li>'+
                                        '<li>USA </li>'+
                                        '<li>Algeria</li>'+
                                        '<li>Andorra</li>'+
                                        '<li>Angola</li>'+
                                        '<li>Anguilla</li>'+
                                        '<li>Antigua &amp; Barbuda</li>'+
                                        '<li>Argentina</li>'+
                                        '<li>Armenia</li>'+
                                        '<li>Aruba</li>'+
                                        '<li>Australia</li>'+
                                        '<li>Austria</li>'+
                                        '<li>Azerbaijan</li>'+
                                        '<li>Bahamas</li>'+
                                        '<li>Bahrain</li>'+
                                        '<li>Bangladesh</li>'+
                                        '<li>Barbados</li>'+
                                        '<li>Belarus</li>'+
                                        '<li>Belgium</li>'+
                                        '<li>Belize</li>'+
                                        '<li>Benin</li>'+
                                        '<li>Bermuda</li>'+
                                        '<li>Bhutan</li>'+
                                        '<li>Bolivia</li>'+
                                        '<li>Bosnia Herzegovina</li>'+
                                        '<li>Botswana</li>'+
                                        '<li>Brazil</li>'+
                                        '<li>Brunei</li>'+
                                        '<li>Bulgaria</li>'+
                                        '<li>Burkina Faso</li>'+
                                        '<li>Burundi</li>'+
                                        '<li>Cambodia</li>'+
                                        '<li>Cameroon</li>'+
                                        '<li>Canada</li>'+
                                        '<li>Cape Verde Islands</li>'+
                                        '<li>Cayman Islands</li>'+
                                        '<li>Central African Republic</li>'+
                                        '<li>Chile</li>'+
                                        '<li>China</li>'+
                                        '<li>Colombia</li>'+
                                        '<li>Comoros</li>'+
                                        '<li>Congo</li>'+
                                        '<li>Cook Islands</li>'+
                                        '<li>Costa Rica</li>'+
                                        '<li>Croatia</li>'+
                                        '<li>Cuba</li>'+
                                        '<li>Cyprus North</li>'+
                                        '<li>Cyprus South</li>'+
                                        '<li>Czech Republic</li>'+
                                        '<li>Denmark</li>'+
                                        '<li>Djibouti</li>'+
                                        '<li>Dominica</li>'+
                                        '<li>Dominican Republic</li>'+
                                        '<li>Ecuador</li>'+
                                        '<li>Egypt</li>'+
                                        '<li>El Salvador</li>'+
                                        '<li>Equatorial Guinea</li>'+
                                        '<li>Eritrea</li>'+
                                        '<li>Estonia</li>'+
                                        '<li>Ethiopia</li>'+
                                        '<li>Falkland Islands</li>'+
                                        '<li>Faroe Islands</li>'+
                                        '<li>Fiji</li>'+
                                        '<li>Finland</li>'+
                                        '<li>France</li>'+
                                        '<li>French Guiana</li>'+
                                        '<li>French Polynesia</li>'+
                                        '<li>Gabon</li>'+
                                        '<li>Gambia</li>'+
                                        '<li>Georgia</li>'+
                                        '<li>Germany</li>'+
                                        '<li>Ghana</li>'+
                                        '<li>Gibraltar</li>'+
                                        '<li>Greece</li>'+
                                        '<li>Greenland</li>'+
                                        '<li>Grenada</li>'+
                                        '<li>Guadeloupe</li>'+
                                        '<li>Guam</li>'+
                                        '<li>Guatemala</li>'+
                                        '<li>Guinea</li>'+
                                        '<li>Guinea – Bissau</li>'+
                                        '<li>Guyana</li>'+
                                        '<li>Haiti</li>'+
                                        '<li>Honduras</li>'+
                                        '<li>Hong Kong</li>'+
                                        '<li>Hungary</li>'+
                                        '<li>Iceland</li>'+
                                        '<li>India</li>'+
                                        '<li>Indonesia</li>'+
                                        '<li>Iran</li>'+
                                        '<li>Iraq</li>'+
                                        '<li>Ireland</li>'+
                                        '<li>Israel</li>'+
                                        '<li>Italy</li>'+
                                        '<li>Jamaica</li>'+
                                        '<li>Japan</li>'+
                                        '<li>Jordan</li>'+
                                        '<li>Kazakhstan</li>'+
                                        '<li>Kenya</li>'+
                                        '<li>Kiribati</li>'+
                                        '<li>Korea North</li>'+
                                        '<li>Kuwait</li>'+
                                        '<li>Kyrgyzstan</li>'+
                                        '<li>Laos</li>'+
                                        '<li>Latvia</li>'+
                                        '<li>Lebanon</li>'+
                                        '<li>Lesotho</li>'+
                                        '<li>Liberia</li>'+
                                        '<li>Libya</li>'+
                                        '<li>Liechtenstein</li>'+
                                        '<li>Lithuania</li>'+
                                        '<li>Luxembourg</li>'+
                                        '<li>Macao</li>'+
                                        '<li>Macedonia</li>'+
                                        '<li>Madagascar</li>'+
                                        '<li>Malawi</li>'+
                                        '<li>Malaysia</li>'+
                                        '<li>Maldives</li>'+
                                        '<li>Mali</li>'+
                                        '<li>Malta</li>'+
                                        '<li>Marshall Islands</li>'+
                                        '<li>Martinique</li>'+
                                        '<li>Mauritania</li>'+
                                        '<li>Mayotte</li>'+
                                        '<li>Mexico</li>'+
                                        '<li>Micronesia</li>'+
                                        '<li>Moldova</li>'+
                                        '<li>Monaco</li>'+
                                        '<li>Mongolia</li>'+
                                        '<li>Montserrat</li>'+
                                        '<li>Morocco</li>'+
                                        '<li>Mozambique</li>'+
                                        '<li>Myanmar</li>'+
                                        '<li>Namibia</li>'+
                                        '<li>Nauru</li>'+
                                        '<li>Nepal</li>'+
                                        '<li>Netherlands</li>'+
                                        '<li>New Caledonia</li>'+
                                        '<li>New Zealand</li>'+
                                        '<li>Nicaragua</li>'+
                                        '<li>Niger</li>'+
                                        '<li>Nigeria</li>'+
                                        '<li>Niue</li>'+
                                        '<li>Norfolk Islands</li>'+
                                        '<li>Northern Marianas</li>'+
                                        '<li>Norway</li>'+
                                        '<li>Oman</li>'+
                                        '<li>Palau</li>'+
                                        '<li>Panama</li>'+
                                        '<li>Papua New Guinea</li>'+
                                        '<li>Paraguay</li>'+
                                        '<li>Peru</li>'+
                                        '<li>Philippines</li>'+
                                        '<li>Poland</li>'+
                                        '<li>Portugal</li>'+
                                        '<li>Puerto Rico</li>'+
                                        '<li>Qatar</li>'+
                                        '<li>Reunion</li>'+
                                        '<li>Romania</li>'+
                                        '<li>Russia</li>'+
                                        '<li>Rwanda</li>'+
                                        '<li>San Marino</li>'+
                                        '<li>Sao Tome &amp; Principe</li>'+
                                        '<li>Saudi Arabia</li>'+
                                        '<li>Senegal</li>'+
                                        '<li>Serbia</li>'+
                                        '<li>Seychelles</li>'+
                                        '<li>Sierra Leone</li>'+
                                        '<li>Singapore</li>'+
                                        '<li>Slovak Republic</li>'+
                                        '<li>Slovenia</li>'+
                                        '<li>Solomon Islands</li>'+
                                        '<li>Somalia</li>'+
                                        '<li>South Africa</li>'+
                                        '<li>Spain</li>'+
                                        '<li>Sri Lanka</li>'+
                                        '<li>St. Helena</li>'+
                                        '<li>St. Kitts</li>'+
                                        '<li>St. Lucia</li>'+
                                        '<li>Sudan</li>'+
                                        '<li>Suriname</li>'+
                                        '<li>Swaziland</li>'+
                                        '<li>Sweden</li>'+
                                        '<li>Switzerland</li>'+
                                        '<li>Syria</li>'+
                                        '<li>Taiwan</li>'+
                                        '<li>Tajikstan</li>'+
                                        '<li>Thailand</li>'+
                                        '<li>Togo</li>'+
                                        '<li>Tonga</li>'+
                                        '<li>Trinidad &amp; Tobago</li>'+
                                        '<li>Tunisia</li>'+
                                        '<li>Turkey</li>'+
                                        '<li>Turkmenistan</li>'+
                                        '<li>Turkmenistan</li>'+
                                        '<li>Turks &amp; Caicos Islands</li>'+
                                        '<li>Tuvalu</li>'+
                                        '<li>Uganda</li>'+
                                        '<li>UK</li>'+
                                        '<li>Ukraine</li>'+
                                        '<li>United Arab Emirates</li>'+
                                        '<li>Uruguay</li>'+
                                        '<li>Uzbekistan</li>'+
                                        '<li>Vanuatu</li>'+
                                        '<li>Vatican City</li>'+
                                        '<li>Venezuela</li>'+
                                        '<li>Vietnam</li>'+
                                        '<li>Virgin Islands – British</li>'+
                                        '<li>Virgin Islands – US</li>'+
                                        '<li>Wallis &amp; Futuna</li>'+
                                        '<li>Yemen (North)</li>'+
                                        '<li>Yemen (South)</li>'+
                                        '<li>Zambia</li>'+
                                        '<li>Zimbabwe</li>'+
                                    '</ul>' +
                                '</div>' +
                            '</td>' +
                            '<td><input type="text" class="nv_input pone phone" name="guest_phone" placeholder="연락처를 입력해주세요." value="'+visitInfo.visitor.phone+'"/></td>' +
                            '<td><input type="text" class="nv_input" name="guest_company" placeholder="회사명을 입력해주세요." value="'+visitInfo.visitor.company+'" /></td>' +
                            '<td style="display: none;">'+(module_html.tbody.component.carray(visitInfo.carray))+'</td>'+
                            '<td><button type="button" class="nv_red_button" onclick="removeThisRow(this);">삭제</button></td>' +
                        '</tr>';
                }
            }
        },
        modal : {
            checkedState : function(target, value) {
                if(value.length!=0) {
                    var splitValue = value.split(',');
                    var returnValue = '';
                    for(var i in splitValue) {
                        if(splitValue[i].trim()==target) {
                            returnValue = 'checked';
                        }
                    }
                    return returnValue;
                }
            },
            dl : function(carray) {
                return  '<dt>반입물품</dt>' +
                        '<dd>' +
                            '<div class="nv_select_box">' +
                                '<p id="carryStuffWare">'+carray.ware+'</p>' +
                                '<ul>' +
                                    '<li>노트북</li>' +
                                    '<li>PC</li>' +
                                    '<li>USB</li>' +
                                    '<li>테블릿</li>' +
                                    '<li>기타</li>' +
                                '</ul>' +
                            '</div>' +
                        '</dd>' +
                        '<dt>시리얼번호</dt>' +
                        '<dd><input type="text" class="nv_input" name="carryStuffSerialNo" value="'+carray.serial+'" placeholder="시리얼번호를 입력해주세요." /></dd>' +
                        '<dt>사용목적</dt>' +
                        '<dd>' +
                            '<div class="nv_select_box">' +
                                '<p id="carryStuffPurpose">'+carray.purpose+'</p>' +
                                '<ul>' +
                                    '<li>협의</li>' +
                                    '<li>시운전</li>' +
                                    '<li>A/S</li>' +
                                '</ul>' +
                            '</div>' +
                        '</dd>' +
                        '<dt></dt>' +
                        '<dd></dd>' +
                        '<dt>사용유무</dt>' +
                        '<dd class="nv_dd_full nv_check_area">' +
                            '<input type="checkbox" id="n1" name="carryStuffUsed" class="nv_checkbox" '+this.checkedState('LAN 사용', carray.used)+' />' +
                            '<label for="n1">LAN 사용</label>' +
                            '<input type="checkbox" id="n2" name="carryStuffUsed" class="nv_checkbox" '+this.checkedState('USB 사용', carray.used)+' />' +
                            '<label for="n2">USB 사용</label>' +
                            // '<input type="checkbox" id="n3" name="carryStuffUsed" class="nv_checkbox" '+this.checkedState('USB 사용(마우스)', carryStuffUsed)+' />' +
                            // '<label for="n3">USB 사용(마우스)</label>' +
                        '</dd>';
            }
        }   
    }
   
    /**
    * 방문객 외부인 삭제 row 
    * @param this
    */
    function removeThisRow(dom) {
        if ($(dom).parents("tbody").children("tr").length == 1
             && $(dom).parents("tr").index() == 0) {
			$(dom).parents("tr").find("input").val("");
		} else {
			$(dom).parents("tr").remove();
		}
    }

    /**
    * 방문객 외부인 추가 row 
    * @param {Object} visitInfo
    * @param {Enum} visitorType (INSIDE(1) || OUTSIDE(2))
    */
    function addRow(visitInfo, visitorType) {
        var target = $('#visitorTbody');
        if(visitInfo==undefined) {
            visitInfo = new VisitInfo(target.find('tr').length, visitorType);
        }
        target.append(module_html.tbody.tr(visitInfo));
        regenDatepicker();
        setDatePicker();
    }
    
    function init() {
        $('#visitorThead').html(module_html.thead.tr());
        //$('#visitorTbody').html(addRow());
    }
    $(function() {
        //방문객 회사정보 가져오기 및 자동완성
        // callApi.getData('/visitor/company', function (result) {            
        //     $(document).on('focus', 'input[name="guest_company"]' , function() {
        //         $(this).autocomplete({
        //             source : result.map(x=> x.company),
        //             messages: {
        //                 noResults: '',
        //                 results: function() {
        //                     $("ul.ui-autocomplete li").css("background", "white");
        //                 }
        //             }
        //         });
        //     });
        // });
        
        init();
        
        // 방문객 방문 위치정보 가져오기
        callApi.getData('/visitor/worksite', function (result) {
            var siteDepth1 = $('#div_sitedepth1 > ul');
            var siteDepth2 = $('#div_sitedepth2 > ul');
            var siteDepth3 = $('#div_sitedepth3 > ul');

            function removeSiteNameOverlap(element) {
                var _siteName = '';
                element.find('li').each(function(i,e) {
                    if(_siteName==e.innerHTML) this.remove();
                    _siteName = e.innerHTML;
                });
            }

            $.each(result, function(i1, e1) {
                // 1depth
                var depth1 = $('<li>');
                depth1.html(e1.siteDepth1);
                depth1.on('click', function() {
                    siteDepth2.empty();
                    $.each(result, function(i2, e2) {
                        if(e1.siteDepth1==e2.siteDepth1) {
                            // 2depth
                            var depth2 = $('<li>');
                            depth2.html(e2.siteDepth2);
                            depth2.on('click', function() {
                                siteDepth3.empty();
                                $.each(result, function(i3, e3) {
                                    if(e2.siteDepth2==e3.siteDepth2) {
                                        // 3depth
                                        var depth3 = $('<li>');
                                        depth3.html(e3.siteDepth3);
                                        siteDepth3.append(depth3);
                                    }
                                });
                                removeSiteNameOverlap(siteDepth3);
                            });
                            siteDepth2.append(depth2);
                        }
                    });
                    removeSiteNameOverlap(siteDepth2);
                });
                siteDepth1.append(depth1);
            });
            removeSiteNameOverlap(siteDepth1);
        });

        regenDatepicker();
		setDatePicker();
	});

    $(document).on('keydown', '#host_name, input[name="guest_name"]', function(event) {
        if (event.keyCode == 13) event.target.nextElementSibling.click();
        else {
            $("#host_id").val("");
            $("#host_dept").val("");
        }
    });

    $(document).on('click', '.find_modal', function() {
        var target = $(this);
        var targetId = target.next();
        var targetName = target.prev();
        var _targetName = targetName.val().split(']').length==3 ? targetName.val().split(']')[2] : targetName.val();
        callApi.getData('/host-list?hostName=' + _targetName, function (result) {
            if(result.length == 0 ) {
                alert('이름을 정확히 입력해주세요.');
                return;
            }
            $(".nv_modal1").addClass("on");
            var tableTbody = $('#host_table_tbody');
            tableTbody.empty();
            $.each(result, function(i, e) {
                var _tr = $('<tr>');
                _tr.on('click', function() {
                    targetId.val(e.hostID);
                    targetName.val('['+e.company+']['+e.deptCD+']'+e.hostName);
                    $('.nv_modal1').removeClass('on');
                });
                _tr.append('<td>'+e.company+'</td>');
                _tr.append('<td>'+e.deptCD+'</td>');
                _tr.append('<td>'+e.hostName+'</td>');
                tableTbody.append(_tr);
            });
            
        });
    })

    $(document).on('click', '.nv_modal2_open', function() {
        var visitInfo = new VisitInfo();
        var targetTrId = $(this).parent().parent().attr('id');
        $('.nv_modal2 .nv_blue_button').attr('id', targetTrId);
        if($(this).html()=='등록') {
            var target = $('#visitorTbody');
            $('#carryStuffList').html(module_html.modal.dl(visitInfo.carray));
        } else {
            var carryStuffWare = $('#'+targetTrId+' input[name="guest_ware"]').val();
            var carryStuffSerialNo = $('#'+targetTrId+' input[name="guest_serial"]').val();
            var carryStuffPurpose = $('#'+targetTrId+' input[name="guest_purpose"]').val();
            var carryStuffUsed = $('#'+targetTrId+' input[name="guest_used"]').val();
            visitInfo.addCarray(carryStuffWare, carryStuffSerialNo, carryStuffPurpose, carryStuffUsed);
            $('#carryStuffList').html(module_html.modal.dl(visitInfo.carray));
        }
    });

    $(document).on('click', '.nv_modal .nv_blue_button', function() {
        var targetTrId = $(this).attr('id');
        $('#'+targetTrId+' input[name="guest_ware"]').val($('input[name="carryStuffWare"]').val());
        $('#'+targetTrId+' input[name="guest_serial"]').val($('input[name="carryStuffSerialNo"]').val());
        $('#'+targetTrId+' input[name="guest_purpose"]').val($('#carryStuffPurpose').html());

        $('input[name="carryStuffUsed"]:checked').each(function(index, item) {
            let target = $('#'+targetTrId+' input[name="guest_used"]');
            if($('input[name="carryStuffUsed"]:checked').length==1)
                target.val($(this).next().html());
            else
                index==0 ? target.val($(this).next().html()) : target.val(target.val()+', '+$(this).next().html());
        });

        $('.nv_modal2_open').html('수정');
        $('.nv_modal_close').click();
    });

    $(document).on('click', '#visitorInfoSave', function(event) {
        var hostId = $('input[name="host_id"]').val();
        // var hostName = $('input[name="host_name"]').val();
        // var hostDept = $('input[name="host_dept"]').val();
        var planFromDate = $('input[name="plan_from_date"]').val();
        var planToDate = $('input[name="plan_to_date"]').val();
        var visitPurpose = $('#vistorPurpose > p').html();
        var visitPosition1 = $('#div_sitedepth1 > p').html();
        var visitPosition2 = $('#div_sitedepth2 > p').html();
        var visitPosition3 = $('#div_sitedepth3 > p').html();
        var visitorCar = $('input[name="guest_carNo"]').val();
        var visitPurposeDetail = $('#vistorPurposeDetail').val();

        if(visitPosition1 == "선택" || visitPosition2 == "선택" || visitPosition3 == "선택") {
            alert('방문 위치를 선택해 주세요.');
            return;
        }    
        var visitorForm = new FormData();
        visitorForm.append('hostId', hostId);
        // visitorForm.append('hostName', hostName);
        // visitorForm.append('hostDept', hostDept);
        visitorForm.append('planFromDate', planFromDate);
        visitorForm.append('planToDate', planToDate);
        visitorForm.append('visitPurpose', visitPurpose);
        visitorForm.append('visitPurposeDetail', visitPurposeDetail);
        visitorForm.append('visitorCar', visitorCar);
        visitorForm.append('visitPosition1', visitPosition1);
        visitorForm.append('visitPosition2', visitPosition2);
        visitorForm.append('visitPosition3', visitPosition3);

        var visitorId = $('input[name="guest_id"]');
        var visitorName = $('input[name="guest_name"]');
        var visitorBirth = $('input[name="guest_birth"]');
        var visitorPhone = $('input[name="guest_phone"]');
        var visitorWare =  $('input[name="guest_ware"]');
        var visitorSerial = $('input[name="guest_serial"]');
        var visitorPurpose = $('input[name="guest_purpose"]');
        var visitorUsed = $('input[name="guest_used"]');
        var visitorCompany = $('input[name="guest_company"]');
        
        //var visitorCarType = $('input[name="guest_car_type"]');
        //var visitorCar = $('input[name="guest_car"]');
     
        $('#visitorTbody > tr').each(function(i, item) {
            visitorForm.append('visitorId', visitorId[i]!=undefined ? visitorId[i].value : '');
            visitorForm.append('visitorName', visitorName[i]!=undefined ? visitorName[i].value : '');
            visitorForm.append('visitorGender', $('#guest_gender'+i).text());
            visitorForm.append('visitorLocation', $('#guest_location'+i).text());
            visitorForm.append('visitorBirth', visitorBirth[i]!=undefined ? visitorBirth[i].value : '');
            visitorForm.append('visitorPhone', visitorPhone[i]!=undefined ? visitorPhone[i].value : '');
            visitorForm.append('visitorCompany', visitorCompany[i]!=undefined ? visitorCompany[i].value : '');
            visitorForm.append('visitorWare', visitorWare[i].value);
            visitorForm.append('visitorSerial', visitorSerial[i].value);
            visitorForm.append('visitorPurpose', visitorPurpose[i].value);
            visitorForm.append('visitorUsed', visitorUsed[i].value);
            
            // if(visitPurpose=='납품/반출') {
            //     visitorForm.append('visitorCarType', visitorCarType[i].value);
            //     visitorForm.append('visitorCar', visitorCar[i].value);
            // } else {
            //     visitorForm.append('visitorSerial', visitorSerial[i].value);
            //     visitorForm.append('visitorPurpose', visitorPurpose[i].value);
            //     visitorForm.append('visitorUsed', visitorUsed[i].value);
            // }
        });
        callApi.setFormData('/visitor', visitorForm, function(result) {
            alert('정상적으로 등록되었습니다.');
            location.reload();
        });
    });
</script>

<div class="nv_contents_wrap">
	<div class="nv_contents">
		<div class="nv_contents_main_header">
			<h4>방문 대리 신청</h4>
        </div>
        <div class="nv_table_tit regist_tit">
			<h4>접견인정보</h4>
        </div>
        <div class="nv_table_box">
            <dl class="nv_dl_table">
                <dt>접견인</dt>
                <dd class="nv_dd_full">
                    <input type="text" class="nv_input max_200" id="host_name" name="host_name" placeholder="검색 할 접견인 입력">
                    <button type="button" class="nv_blue_button find_modal">찾기</button>
                    <input type="hidden" id="find_id" name="host_id" />
                </dd>
            </dl>
        </div>
		<div class="nv_table_tit regist_tit">
			<h4>방문자 정보</h4>
        </div>
        
        <div class="nv_table_box">
            <dl class="nv_dl_table">
                <%--
                <dt>회사명</dt>
                <dd class="nv_dd_full">
                    <input type="text" class="nv_input max_200" id="guest_company" name="guest_company" placeholder="사업자등록증상 회사명"/>
                </dd>
                --%>
                <dt>방문기간</dt>
                <dd class="nv_dd_full nv_date">
                    <input type="text" class="nv_input max_200 birth" id="plan_from_date" name="plan_from_date" />
                    <span>~</span>
                    <input type="text" class="nv_input max_200 birth" id="plan_to_date" name="plan_to_date" />
                </dd>
                <dt>방문목적</dt>
                <dd class="nv_dd_full">
                    <div class="nv_select_box" id="vistorPurpose">
                        <p>업무</p>
                        <ul>
                            <li>업무</li>
                            <li>납품</li>
                            <li>자재사급</li>
                            <li>반출수송</li>
                            <li>공사(A/S)</li>
                            <li>노동조합</li>
                            <li>기타</li>
                        </ul>
                    </div>
                </dd>
                <dt>방문차량</dt>
                <dd class="nv_dd_full">
                    <input type="text" class="nv_input max_200" id="guest_carNo" name="guest_carNo" placeholder="방문 차량번호"/>
                </dd>
                <dt>방문위치</dt>
                <dd class="nv_dd_full nv_date nv_double_select">
                    <div class="nv_select_box" id= "div_sitedepth1"><p>선택</p><ul></ul></div>
                    <div class="nv_select_box" id= "div_sitedepth2"><p>선택</p><ul></ul></div>
                    <div class="nv_select_box" id= "div_sitedepth3"><p>선택</p><ul></ul></div>
                </dd>
            </dl>
        </div>
        <div class="nv_table_tit nv_btn_area_tit">
            <h4>방문객 정보</h4>
            <div style="display: none;"><input type="file" id="excelFile" /></div>
            <div class="btn_area">
                <%--
                <button type="button" class="nv_green_button down_icon_btn tpc_skip m_skip" onclick="javascript:location.href='/upload/sample/visitsample.xlsx'">엑셀 다운로드</button>
                <button type="button" class="nv_green_button up_icon_btn tpc_skip m_skip" onclick="$('#excelFile').click();">엑셀 등록</button>
                --%>
                <button type="button" class="nv_blue_button add_icon_btn" onclick="addRow(undefined, 2);">방문객 추가(외부인)</button>
                <button type="button" class="nv_blue_button add_icon_btn" onclick="addRow(undefined, 1);">방문객 추가(임직원)</button>
            </div>
        </div>
        <div class="nv_table_box">
            <%-- <table class="nv_table textcenter tpc_skip m_skip"> --%>
            <table class="nv_table textcenter">
                <thead id="visitorThead"></thead>
                <tbody id="visitorTbody"></tbody>
            </table>
            <div class="btn_area nv_page_bottomarea">
                <button type="button" id="visitorInfoSave" class="nv_blue_button m_full_btn">입력 완료</button>
            </div>
        </div>   
	</div>
</div>

<div class="nv_modal nv_modal1">
    <div class="nv_modal_container">
        <div class="nv_modal_header">
            <h2 id="find-host-title">접견인 선택</h2>
            <p class="nv_modal_close">닫기</p>
        </div>
        <div class="nv_modal_contents">
            <%--
            <!-- <div>
                <input type="text" class="nv_input" style="width: 85%;" id="find_name" name="find_name" placeholder="">
                <button type="button" class="nv_blue_button find_modal">찾기</button>
                <input type="hidden" id="find_id" name="find_id" />
            </div> -->
            --%>
            <%-- <table class="nv_table textcenter tpc_skip m_skip"> --%>
            <table class="nv_table textcenter">
				<thead>
					<tr>
                        <th>회사</th>
                        <th>부서</th>
                        <th>이름</th>
					</tr>
				</thead>
				<tbody id="host_table_tbody"></tbody>
			</table>
        </div>
    </div>
</div>

<div class="nv_modal nv_modal2">
    <div class="nv_modal_container" style="max-width: 650px;">
        <div class="nv_modal_header">
            <h2>반입물품</h2>
            <p class="nv_modal_close">닫기</p>
        </div>
        <div class="nv_modal_contents">
            <dl class="nv_dl_table" id="carryStuffList"></dl>
            <div class="btn_area" style="clear: both;display: block;padding-top: 10px;margin-bottom: 0px;">
                <button type="button" class="nv_blue_button">확인</button>
            </div>
        </div>
    </div>
</div>