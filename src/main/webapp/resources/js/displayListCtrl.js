app.controller("displayListCtrl", function($scope, $http,$timeout) {
    
    $scope.loans;
    $scope.c_user = null;
    
    $scope.update = function() {
        var response = $http.get("getActiveLoans");
        response.success(function(data) {
          $scope.loans = data;
          
        });

        response.error(function(data, status, headers) {
            alert( "Exception details: " + JSON.stringify({data: data,status:status,headers:headers}));
        });
        
    };
    
    
    $scope.getCurrentUser = function() {
        var response = $http.get("getCurrentUser");
        response.success(function(data) {
          $scope.c_user = data;
          
          if ($scope.c_user!==null) {
            $scope.name = $scope.c_user['name'];
          }
          else {
            $scope.name = "";
          }
          
        });

        response.error(function(data, status, headers) {
            alert( "Exception details: " + JSON.stringify({data: data,status:status,headers:headers}));
        });
        
    };
    
    
    $scope.getCurrentUser();
    
    $scope.update();
    
    $scope.index = 0;
    $scope.notify = false;
    $scope.msg = "";
    

    
    $scope.createRepayment = function(i) {
        
        if ($scope.loans[i]["amt_repaid"]<=0) {
                return;
        }

        $scope.loans[i]["flag2"] = "false";
        $scope.loans[i]["flag1"] = "true";
        
        var params = "id="+encodeURIComponent($scope.loans[i]["loan_no"])+
                "&amt="+encodeURIComponent($scope.loans[i]["amt_repaid"]);
        
        var response = $http({
            method: 'POST',
            url: 'createRepayment',
            data: params,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        
        response.success(function(data) {
          $scope.update();
          
          $scope.msg = "Repayment Created!";
          $scope.notify = true;

          $scope.loans[i]["amt_repaid"] = 0;
          
          $timeout(function(){
            $scope.notify = false;
          }, 1300);
          
        });

        response.error(function(data, status, headers) {
            alert( "Exception details: " + JSON.stringify({data: data,status:status,headers:headers}));
        });
        
    };
    
    $scope.updateRepayment = function(i) {
        
        if ($scope.loans[i]["amt_repaid"]<=0) {
                return;
        }
        
        var params = "id="+encodeURIComponent($scope.loans[i]["loan_no"])+
                "&amt="+encodeURIComponent($scope.loans[i]["amt_repaid"]);
        
        var response = $http({
            method: 'POST',
            url: 'updateRepayment',
            data: params,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        
        response.success(function(data) {
          $scope.update();
          
          $scope.msg = "Repayment Updated!";
          $scope.notify = true;
     
          $scope.loans[i]["amt_repaid"] = 0;
          
          $timeout(function(){
            $scope.notify = false;
          }, 1300);
          
        });

        response.error(function(data, status, headers) {
            alert( "Exception details: " + JSON.stringify({data: data,status:status,headers:headers}));
        });
        
    };
    
    $scope.deleteRepayment = function(i) {
        $scope.loans[i]["flag2"] = "true";
        $scope.loans[i]["flag1"] = "false";
        
        var params = "id="+encodeURIComponent($scope.loans[i]["loan_no"]);
        
        var response = $http({
            method: 'POST',
            url: 'deleteRepayment',
            data: params,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        
        response.success(function(data) {
          $scope.update();
          
          $scope.msg = "Repayment Deleted!";
          $scope.notify = true;
          
          $timeout(function(){
            $scope.notify = false;
          }, 1300);
          
        });

        response.error(function(data, status, headers) {
            alert( "Exception details: " + JSON.stringify({data: data,status:status,headers:headers}));
        });
        
    };
    
});