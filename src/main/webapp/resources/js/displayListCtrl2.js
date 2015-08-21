app.controller("displayListCtrl2", function($scope, $http,$timeout) {
    
    $scope.update = function() {
        var response = $http.get("getCustomers");
        
        response.success(function(data) {
          $scope.users = data;
          
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
    
    $scope.c_user = null;
    $scope.getCurrentUser();    
    
    $scope.update();
    
    
    $scope.index = 0;
    $scope.notify = false;
    $scope.msg = "Loan Created!";
    
    $scope.createLoan = function(i) {
        
        if ($scope.users[i]["amt_owed"]<=0) {
                return;
        }
        
        var params = "cust_id_no="+encodeURIComponent($scope.users[i]["id"])+
                "&amt="+encodeURIComponent($scope.users[i]["amt_owed"]);
        
        var response = $http({
            method: 'POST',
            url: 'createLoan',
            data: params,
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        });
        
        response.success(function(data) {
          $scope.update();
          
          $scope.notify = true;
          $scope.users[i]["amt_owed"] = 0;
          
          $timeout(function(){
            $scope.notify = false;
          }, 1300);
          
        });

        response.error(function(data, status, headers) {
            alert( "Exception details: " + JSON.stringify({data: data,status:status,headers:headers}));
        });
        
    };
    
    
});