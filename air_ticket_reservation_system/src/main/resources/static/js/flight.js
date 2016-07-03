var app = angular.module('flight', [ 'ui.bootstrap', 'ui.router', 'ui.grid',
		'ui.grid.selection', 'ui.grid.autoResize' ]);

app.factory('flightService', function() {
	var from;
	var to;
	var fromDate;
	var toDate;
	return {
		setFrom : function(airport) {
			from = airport;
		},
		getFrom : function() {
			return from;
		},
		setTo : function(airport) {
			to = airport;
		},
		getTo : function() {
			return to;
		},
		setFromDate : function(date) {
			fromDate = date;
		},
		getFromDate : function() {
			return fromDate;
		},
		setToDate : function(date) {
			toDate = date;
		},
		getToDate : function() {
			return toDate;
		}
	};
});

app
		.config(function($logProvider, $stateProvider, $urlRouterProvider,
				$httpProvider) {
			$logProvider.debugEnabled(true);
			$urlRouterProvider.otherwise("/index.html");

			$stateProvider.state('home', {
				url : "/"
			}).state('login', {
				url : "/login",
				controller : "login",
				templateUrl : '/login.html'
			}).state('voucher', {
				url : "/voucher",
				controller : "voucher",
				templateUrl : '/voucher.html'
			});
			$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		});

app.controller('home', function($http) {
	var self = this;
	$http.get('/resource/').then(function(response) {
		self.greeting = response.data;
	})
});

app.controller('login', function($rootScope, $scope, $uibModal, $log, $http) {

	$log.debug('Modal handler initialization');

	$scope.animationsEnabled = true;

	$rootScope.openLogin = function(size) {

		var modalInstance = $uibModal.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'myModalContent.html',
			controller : 'ModalInstanceCtrl',
			size : size
		});

		modalInstance.result.then(function(selectedItem) {
			$scope.selected = selectedItem;
		}, function() {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

	$scope.toggleAnimation = function() {
		$scope.animationsEnabled = !$scope.animationsEnabled;
	};

	$log.debug('login initialized!!!');

	$rootScope.openLogin();
});

app.controller('initialization', function($scope, $log) {
	$log.debug('initialized');
	$scope.title = "LionH Company - The best company to fly with";
});

app
		.controller(
				'listOfFlights',
				function($rootScope, $scope, $log, $http, flightService, $state) {
					var rowHeight = 35; // your row height
					var headerHeight = 80; // your header height
					$scope.oneway = {
						data : [],
						enableRowSelection : true,
						enableSelectAll : false,
						selectionRowHeaderWidth : 35,
						rowHeight : rowHeight,
						showGridFooter : true
					};
					$scope.oneway.columnDefs = [ {
						name : 'date',
						displayName : 'Departure Date',
						type : 'date',
						cellFilter : 'date:\'yyyy-MM-dd\''
					}, {
						name : 'origin',
						displayName : 'From'
					}, {
						name : 'destination',
						displayName : 'To'
					}, {
						name : 'price',
						displayName : 'Price(€)'
					}, {
						name : 'availableSeats',
						displayName : 'Available Seats'
					} ];
					$scope.oneway.onRegisterApi = function(gridApi) {
						$log.debug('onRegisterApi');
						// set gridApi on scope
						$scope.onewayApi = gridApi;
						$scope.rowsSelected = gridApi.selection
								.getSelectedRows();
						gridApi.selection.on.rowSelectionChanged($scope,
								function(row) {
									var msg = 'row selected ' + row.isSelected;
									$scope.isOneWaySelected = row.isSelected;
									$log.debug(msg);
								});

						gridApi.selection.on.rowSelectionChangedBatch($scope,
								function(rows) {
									var msg = 'rows changed ' + rows.length;
									$log.debug(msg);
								});
					};

					$scope.onewayHeight = function() {
						return {
							height : ($scope.oneway.data.length * rowHeight + headerHeight)
									+ "px"
						};
					};
					$scope.roundtrip = {
						data : [],
						enableRowSelection : true,
						enableSelectAll : false,
						selectionRowHeaderWidth : 35,
						rowHeight : rowHeight,
						showGridFooter : true
					};
					$scope.roundtrip.columnDefs = [ {
						name : 'date',
						displayName : 'Return Date',
						type : 'date',
						cellFilter : 'date:\'yyyy-MM-dd\''
					}, {
						name : 'origin',
						displayName : 'From'
					}, {
						name : 'destination',
						displayName : 'To'
					}, {
						name : 'price',
						displayName : 'Price(€)'
					}, {
						name : 'availableSeats',
						displayName : 'Available Seats'
					} ];

					$scope.roundtrip.onRegisterApi = function(gridApi) {
						$log.debug('onRegisterApi');
						// set gridApi on scope
						$scope.roundtripApi = gridApi;
						$scope.roundtripRowsSelected = gridApi.selection
								.getSelectedRows();
						gridApi.selection.on
								.rowSelectionChanged(
										$scope,
										function(row) {
											var msg = 'row selected '
													+ row.isSelected;
											$scope.isRoundTripSelected = row.isSelected;
											$log.debug(msg);
										});

						gridApi.selection.on.rowSelectionChangedBatch($scope,
								function(rows) {
									var msg = 'rows changed ' + rows.length;
									$log.debug(msg);
								});
					};

					$scope.roundtripHeight = function() {
						return {
							height : ($scope.roundtrip.data.length * rowHeight + headerHeight)
									+ "px"
						};
					};
					$log.debug('Search button eventListener');
					$scope.search = function() {
						$scope.alert = undefined;
						if (angular.isUndefined(flightService.getFrom())) {
							$scope.alert = "please review the airport departure";
						} else if (angular.isUndefined(flightService.getTo())) {
							$scope.alert = "please review the airport arrival";
						} else if (flightService.getFromDate() >= flightService
								.getToDate()) {
							$scope.alert = "please review the date. The date of return must be at least one day after the date of departure";
						} else {
							$http.get('/searchflights/', {
								params : {
									origin : flightService.getFrom().id,
									destination : flightService.getTo().id,
									dateFrom : flightService.getFromDate(),
									dateTo : flightService.getToDate(),
								}
							}).success(function(data) {
								$scope.oneway.data = data.oneway;
								$scope.roundtrip.data = data.roundtrip;
							})
						}
					}
					$scope.book = function() {
						var onewayParam = $scope.onewayApi.selection
								.getSelectedRows()[0].id;
						var roundTripParam = undefined;
						if ($scope.roundtripApi.selection.getSelectedRows().length > 0) {
							roundTripParam = $scope.roundtripApi.selection
									.getSelectedRows()[0].id;
						}
						$http.get('/bookFlight/', {
							params : {
								onewayFlight : onewayParam,
								roundTripFlight : roundTripParam,
								numAdult : 1,
								numYouth : 0,
								numChildren : 0
							}
						}).then(
								function successCallback(data) {
									if (!$rootScope.authenticated) {
										$state.go('login');
										if ($rootScope.openLogin) {
											$rootScope.openLogin();
										}
									}
									$log.debug($scope.roundtripApi.selection
											.getSelectedRows());
									$state.go('voucher');
									if ($rootScope.openVoucher) {
										$rootScope.openVoucher();
									}
								}, function errorCallback(data) {
									if (data.status == 403) {
										$state.go('login');
										if ($rootScope.openLogin) {
											$rootScope.openLogin();
										}
									}
								})
					}
				});

app.controller('dropDownControl',
		function($scope, $log, $filter, flightService) {
			$scope.departure = 'Select date and airport of departure';
			$scope.back = 'Round Trip';
			$scope.toggleShow = false;
			$scope.dt = new Date();
			$scope.airports = [ {
				id : 1,
				name : 'New York'
			}, {
				id : 2,
				name : 'Rome'
			}, {
				id : 3,
				name : 'Madrid'
			} ];
			$scope.airport = {
				name : 'Select Airport'
			};
			$scope.status = {
				isopen : false
			};
			$scope.init = function() {
				flightService.setFromDate(new Date());
				flightService.setToDate(new Date());
			}

			$scope.selectFrom = function($event, $data) {
				$event.preventDefault();
				$event.stopPropagation();
				$scope.status.isopen = !$scope.status.isopen;
				$log.debug('Selected item is now: ', $data.id, $data.name);
				$scope.airport = $data;
				flightService.setFrom($data);
			};

			$scope.selectTo = function($event, $data) {
				$event.preventDefault();
				$event.stopPropagation();
				$scope.status.isopen = !$scope.status.isopen;
				$log.debug('Selected item is now: ', $data.id, $data.name);
				$scope.airport = $data;
				flightService.setTo($data);
			};

			$scope.selectFromDate = function($data) {
				$log.debug('Selected item is now: ', $data);
				$scope.toggleShow = false;
				flightService.setFromDate($data);
			};

			$scope.selectToDate = function($data) {
				$log.debug('Selected item is now: ', $data);
				$scope.toggleShow = false;
				flightService.setToDate($data);
			};

			$scope.appendToEl = angular.element(document
					.querySelector('#dropdown-long-content'));
		});

// Please note that $uibModalInstance represents a modal window (instance)
// dependency.
// It is not the same as the $uibModal service used above.

app.controller('ModalInstanceCtrl', function($rootScope, $scope, $location,
		$http, $uibModalInstance) {

	var authenticate = function(credentials, callback) {

		var headers = credentials ? {
			authorization : "Basic "
					+ btoa(credentials.username + ":" + credentials.password)
		} : {};

		$http.get('user', {
			headers : headers
		}).then(function(response) {
			if (response.data.name) {
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback();
		}, function() {
			$rootScope.authenticated = false;
			callback && callback();
		});

	}

	authenticate();
	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function() {
			if ($rootScope.authenticated) {
				$location.path("/");
				$scope.error = false;
				$uibModalInstance.close($scope.selected.item);
			} else {
				$location.path("/login");
				$scope.error = true;
			}
		});
	};

	$scope.ok = function() {
		$scope.login();
	};

	$scope.cancel = function() {
		$uibModalInstance.dismiss('cancel');
	};
});

app.controller('voucher', function($rootScope, $scope, $uibModal, $log, $http) {

	$log.debug('Modal Voucher handler initialization');

	$scope.animationsEnabled = true;

	$rootScope.openVoucher = function(size) {

		var modalInstance = $uibModal.open({
			animation : $scope.animationsEnabled,
			templateUrl : 'myModalContent.html',
			controller : 'voucherController',
			size : size
		});

		modalInstance.result.then(function(selectedItem) {
			$scope.selected = selectedItem;
		}, function() {
			$log.info('Modal dismissed at: ' + new Date());
		});

	};

	$scope.toggleAnimation = function() {
		$scope.animationsEnabled = !$scope.animationsEnabled;
	};

	$rootScope.openVoucher();

});

app.controller('voucherController', function($rootScope, $scope, $uibModal, $log, $window) {
	$scope.print = function() {
		$log.debug('print Voucher');
		$window.open('/printVoucher.pdf');
	}
});