$(document).ready(function () {
    var default_table = $('.datatable').DataTable({});

    var default_no_pagination = $('.default_no_pagination').DataTable({
        "bPaginate": false,
        "bFilter": true
    });

    var default_no_addon = $('.datatable_no_addon').DataTable({
        "bPaginate": false,
        "bFilter": false,
        "bInfo" : false,
        "bSort" : false
    });
});