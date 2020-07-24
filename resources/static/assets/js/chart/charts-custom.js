var lang = getUrlParam("lang");
 function getUrlParam(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	    var r = parent.location.search.substr(1).match(reg);  // 匹配目标参数
	    if (r != null) return unescape(r[2]);
	     return null; // 返回参数值
}
$(function() {
	//查询一年的数据上传量
	var monthValue=[];
	$.ajax({
		type : 'POST',
		url : '/listMonth',
		async : false,
		//dataType:"json",
		success : function(data) {
			monthValue=data.map(function (item) {
			　　　　 return item['value']; 
			}); 
		}
	})
	//查询各专业的数据占比图
	var menuName=[];
	var menuValue=[];
		$.ajax({
		type : 'POST',
		url : '/listMenu',
		async : false,
		success : function(data) {
			menuName=data.map(function (item) {
			　　　　 return item['name']; 
			});
			menuValue=data.map(function (item) {
			　　　　 return item['value']; 
			});
		}
	})
	//查询各类文档占比图
	var formatName=[];
	var formatValue=[];
	$.ajax({
		type : 'POST',
		url : '/listformat',
		async : false,
		success : function(data) {
			formatName=data.map(function (item) {
			　　　　 return item['name']; 
			});
			formatValue=data.map(function (item) {
			　　　　 return item['value']; 
			});
		}
	})
	//查询文件密级进行文档占比图
	var securityName=[];
	var securityValue=[];
	$.ajax({
		type : 'POST',
		url : '/listSecurity',
		async : false,
		success : function(data) {
			securityName=data.map(function (item) {
			　　　　 return item['name']; 
			});
			securityValue=data.map(function (item) {
			　　　　 return item['value']; 
			});
		}
	})
	//查询各专业例题数目占比图
	var itemName=[];
	var itemValue=[];
	$.ajax({
		type : 'POST',
		url : '/listMenuCount',
		async : false,
		success : function(data) {
			itemName=data.map(function (item) {
			　　　　 return item['name']; 
			});
			itemValue=data.map(function (item) {
			　　　　 return item['value']; 
			});
			console.log(itemName)
			console.log(itemValue)
			var sum =0 
			for(var i=0;i<itemValue.length;i++){
				if(itemValue[i]!=0){
					sum=sum+parseInt(itemValue[i])
				}else{
					itemValue.splice(i,1);
				}
			} 
			document.getElementById("itemCount").innerHTML=sum
		}
	})
	var violet = '#DF99CA',
		red = '#F0404C',
		green = '#7CF29C';
var  grenItem=["#367517","#489620","#50A625","#5BBD2B","#83C75D","#AFD788","#00AE72","#67BF7F"];
	// ------------------------------------------------------- //
	// Charts Gradients
	// ------------------------------------------------------ //
	var ctx1 = $("canvas").get(0).getContext("2d");
	var gradient1 = ctx1.createLinearGradient(150, 0, 150, 300);
	gradient1.addColorStop(0, 'rgba(210, 114, 181, 0.91)');
	gradient1.addColorStop(1, 'rgba(177, 62, 162, 0)');

	var gradient2 = ctx1.createLinearGradient(10, 0, 150, 300);
	gradient2.addColorStop(0, 'rgba(252, 117, 176, 0.84)');
	gradient2.addColorStop(1, 'rgba(250, 199, 106, 0.92)');

	// ------------------------------------------------------- //
	// Pie Chart
	//查询一年的数据上传量
	// ------------------------------------------------------ //
//	var PIECHART = $('#pieChart1');
//	var myPieChart = new Chart(PIECHART, {
//		type: 'line',
//		options: {
//			legend: {
//				display: false
//			},
//			scales: {
//				xAxes: [{
//					display: true,
//					gridLines: {
//						color: '#fff'
//					}
//				}],
//				yAxes: [{
//					display: true,
//					ticks: {
//						min: 20
//					},
//					gridLines: {
//						color: '#fff'
//					}
//				}]
//			},
//		},
//		data: {
//			labels: [1, 2, 3, 4, 5, 6, 7, 8, 9,10, 11, 12],
//			datasets: [{
//				//label: "Data Set One",
//				fill: true,
//				lineTension: 0.3,
//				backgroundColor: gradient1,
//				borderColor: 'rgba(210, 114, 181, 0.91)',
//				borderCapStyle: 'butt',
//				borderDash: [],
//				borderDashOffset: 0.0,
//				borderJoinStyle: 'miter',
//				borderWidth: 2,
//				pointBorderColor: gradient1,
//				pointBackgroundColor: "#fff",
//				pointBorderWidth: 2,
//				pointHoverRadius: 5,
//				pointHoverBackgroundColor: gradient1,
//				pointHoverBorderColor: "rgba(220,220,220,1)",
//				pointHoverBorderWidth: 2,
//				pointRadius: 1,
//				pointHitRadius: 10,
//				data: monthValue,
//				spanGaps: false
//			}]
//		}
//	});

	// ------------------------------------------------------- //
	// Pie Chart
	// ------------------------------------------------------ //
	var PIECHART = $('#pieChart2');
	var myPieChart = new Chart(PIECHART, {
		type: 'doughnut',
		options: {
			cutoutPercentage: 90,
			legend: {
				display: false
			}
		},
		data: {
			labels: [
				"First",
				"Second"
			],
			datasets: [{
				data: [300, 50],
				borderWidth: [0, 0],
				backgroundColor: [
					violet,
					"#eee"
				],
				hoverBackgroundColor: [
					violet,
					"#eee"
				]
			}]
		}
	});
	// ------------------------------------------------------- //
	// Pie Chart
	// ------------------------------------------------------ //
	var PIECHART = $('#pieChart3');
	var myPieChart = new Chart(PIECHART, {
		type: 'doughnut',
		options: {
			cutoutPercentage: 90,
			legend: {
				display: false
			}
		},
		data: {
			labels: [
				"First",
				"Second",
				"Third"
			],
			datasets: [{
				data: [250, 200],
				borderWidth: [0, 0],
				backgroundColor: [
					green,
					"#eee",
				],
				hoverBackgroundColor: [
					green,
					"#eee",
				]
			}]
		}
	}); // ------------------------------------------------------- //
	// Pie Chart
	//查询系统中各专业例题数目占比图
	// ------------------------------------------------------ //
	var PIECHART = $('#pieChart4');
	var myPieChart = new Chart(PIECHART, {
		type: 'doughnut',
		options: {
			cutoutPercentage: 90,
			
		},
		data: {
			labels: itemName,
			datasets: [{
				data: itemValue,
				borderWidth: 0,
				backgroundColor: [
'#df99ca',
'#c374ab',
"#a44e8a",
"#81376a"
				],
				hoverBackgroundColor: [
'#df99ca',
'#c374ab',
"#a44e8a",
"#81376a"
				]
			}]
		}
	});
	
	// ------------------------------------------------------- //
	// Line Chart
	//查询一年的数据上传量
	// ------------------------------------------------------ //
	var monthName;
	if (lang != "en_US") {
		monthName=[1, 2, 3, 4, 5, 6, 7, 8, 9,10, 11, 12]
	}else{
		monthName=["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
	}
	var LINECHARTEXMPLE = $('#lineChartExample');
	var lineChartExample = new Chart(LINECHARTEXMPLE, {
		type: 'line',
		options: {
			legend: {
				display: false
			},
			scales: {
				xAxes: [{
					display: true,
					gridLines: {
						color: '#fff'
					}
				}],
				yAxes: [{
					display: true,
					ticks: {
						max: 100,
						min: 20
					},
					gridLines: {
						color: '#fff'
					}
				}]
			},
		},
		data: {
			labels: monthName,
			datasets: [{
			//	label: "Data Set One",
				fill: true,
				lineTension: 0.3,
				backgroundColor: gradient1,
				borderColor: 'rgba(210, 114, 181, 0.91)',
				borderCapStyle: 'butt',
				borderDash: [],
				borderDashOffset: 0.0,
				borderJoinStyle: 'miter',
				borderWidth: 2,
				pointBorderColor: gradient1,
				pointBackgroundColor: "#fff",
				pointBorderWidth: 2,
				pointHoverRadius: 5,
				pointHoverBackgroundColor: gradient1,
				pointHoverBorderColor: "rgba(220,220,220,1)",
				pointHoverBorderWidth: 2,
				pointRadius: 1,
				pointHitRadius: 10,
				data: monthValue,
				spanGaps: false
			}]
		}
	});



	// ------------------------------------------------------- //
	// Bar Chart
	//查询各类文档占比图
	// ------------------------------------------------------ //
	var BARCHARTEXMPLE = $('#barChartExample1');
	var barChartExample = new Chart(BARCHARTEXMPLE, {
		type: 'bar',
		options: {
			scales: {
				xAxes: [{
					display: true,
					gridLines: {
						color: '#fff'
					}
				}],
				yAxes: [{
					display: true,
					ticks: {
						min: 1
					},
					gridLines: {
						color: '#fff'
						
					}
				}]
			},
			legend: false
		},
		data: {
			labels: formatName,
			datasets: [{
			//	label: "Data Set 1",
				backgroundColor: [
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2
				],
				hoverBackgroundColor: [
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2
				],
				borderColor: [
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2,
					gradient2
				],
				borderWidth: 1,
				data: formatValue,
			}]
		}
	});

	// ------------------------------------------------------- //
	// Doughnut Chart
	//查询各专业的数据占比图
	// ------------------------------------------------------ //
	var DOUGHNUTCHARTEXMPLE = $('#doughnutChartExample');
	var pieChartExample = new Chart(DOUGHNUTCHARTEXMPLE, {
		type: 'doughnut',
		options: {
			cutoutPercentage: 70,
		},
		data: {
			labels: itemName,
			datasets: [{
				data: menuValue,
				borderWidth: 0,
				backgroundColor: [
					'#df99ca',
					'#c374ab',
					"#a44e8a",
					"#81376a"
				],
				hoverBackgroundColor: [
					'#df99ca',
					'#c374ab',
					"#a44e8a",
					"#81376a"
				]
			}]
		}
	});

	var pieChartExample = {
		responsive: true
	};

	// ------------------------------------------------------- //
	// Pie Chart
	//查询文件密级进行文档占比图
	// ------------------------------------------------------ //
	var PIECHARTEXMPLE = $('#pieChartExample');
	var pieChartExample = new Chart(PIECHARTEXMPLE, {
		type: 'pie',
		data: {
			labels:securityName,
			datasets: [{
				data: securityValue,
				borderWidth: 0,
				backgroundColor: grenItem,
				hoverBackgroundColor: grenItem
			}]
		}
	});

	var pieChartExample = {
		responsive: true
	};

});