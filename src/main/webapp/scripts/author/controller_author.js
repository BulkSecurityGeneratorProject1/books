'use strict';

booksApp.controller('AuthorController', function ($scope, resolvedAuthor, Author, resolvedBook) {

        $scope.authors = resolvedAuthor;
        $scope.books = resolvedBook;

        $scope.create = function () {
            Author.save($scope.author,
                function () {
                    $scope.authors = Author.query();
                    $('#saveAuthorModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.author = Author.get({id: id});
            $('#saveAuthorModal').modal('show');
        };

        $scope.delete = function (id) {
            Author.delete({id: id},
                function () {
                    $scope.authors = Author.query();
                });
        };

        $scope.clear = function () {
            $scope.author = {name: null, summary: null, picture: null, id: null};
        };
    });
