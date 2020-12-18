// Dashboard.js
// ====================================================================
// This file should not be included in your project.
// This is just a sample how to initialize plugins or components.
//
// - ThemeOn.net -
$(window).on('load', function() {




    ! function($) {
        "use strict";

        var DataTable = function() {
            this.$dataTableButtons = $("#encounterValidationList2")
        };
        DataTable.prototype.createDataTableButtons = function() {
                this.$dataTableButtons.DataTable({ destroy: true,
                    dom: "<'row'<'col-sm-12 col-md-2'l><'col-sm-12 col-md-7'B><'col-sm-12 col-md-3'f>>rtip",
					
                    buttons: [{
                        extend: 'excelHtml5',
                        text: 'Export Excel',
						 title: function () { return getExportFileName();},
                        exportOptions: {
                            columns: [1, 2, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
                        },
                        customize: function(xlsx) {
                            var sheet = xlsx.xl.worksheets['sheet1.xml'];
                            var col = $('col', sheet);
							
							$('row c[r^="A"]', sheet).attr('s', '51');
							$('row c[r^="C"]', sheet).attr('s', '51');
							$('row c[r^="C"]', sheet).attr('s', '51');
							$('row c[r^="H"]', sheet).attr('s', '51');
							$('row c[r^="I"]', sheet).attr('s', '51');
							$('row c[r^="J"]', sheet).attr('s', '51');
							$('row c[r^="K"]', sheet).attr('s', '51');
							$('row c[r^="L"]', sheet).attr('s', '51');
							$('row c[r^="M"]', sheet).attr('s', '51');
							$('row c[r^="N"]', sheet).attr('s', '51');
							$('row c[r^="O"]', sheet).attr('s', '51');
							$('row c[r^="P"]', sheet).attr('s', '51');
							$('row c[r^="Q"]', sheet).attr('s', '51');
							$('row c[r^="R"]', sheet).attr('s', '51');
														
							
							$('row:nth-child(2) c', sheet).attr( 's', '32' );
					
                        }

                    } ],

                    order: [
                        [2, "desc"]
                    ],
                    "columnDefs": [



                        {
                            "type": "num-fmt",
                            "targets": 10,
                            render: $.fn.dataTable.render.number(',', '.', 0, '$')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 11,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 12,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 13,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 14,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 15,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 16,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 17,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 18,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 19,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 20,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        }

                    ],
                    "pageLength": 10,
                    "scrollX": true,
                    "lengthMenu": [
                        [5, 10, 25, 50, 100, 500, 1000, -1],
                        [5, 10, 25, 50, 100, 500, 1000, "All"]
                    ]
                }).columns(5).search('Incomplete').draw();

            },
            DataTable.prototype.init = function() {
                //creating demo tabels

                $('#datatable').DataTable({
                    dom: "<'row'<'col-sm-12 col-md-2'l><'col-sm-12 col-md-7'B><'col-sm-12 col-md-3'f>>rtip", destroy: true,
                    buttons: [{
                        extend: "excel",
                        className: "btn-primary"
                    }, ],
                    "columnDefs": [

                        {
                            "type": "num-fmt",
                            "targets": 1,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 2,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 3,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 4,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 5,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 6,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 7,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 8,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        }


                    ],
                    order: [
                        [0, "desc"]
                    ],
                    "pageLength": 5,
                    "scrollX": true,
                    "lengthMenu": [
                        [5, 10, 25, 50, 100, 500, 1000, -1],
                        [5, 10, 25, 50, 100, 500, 1000, "All"]
                    ]
                })
                $('#datatable2').DataTable({ destroy: true,
                    dom: "<'row'<'col-sm-12 col-md-2'l><'col-sm-12 col-md-7'B><'col-sm-12 col-md-3'f>>rtip",
                    buttons: [{
                        extend: "excel",
                        className: "btn-primary"
                    }, ],
                    "columnDefs": [

                        {
                            "type": "num-fmt",
                            "targets": 1,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 2,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 3,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 4,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 5,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 6,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 7,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        },
                        {
                            "type": "num-fmt",
                            "targets": 8,
                            render: $.fn.dataTable.render.number(',', '.', 0, '')
                        }


                    ],
                    order: [
                        [0, "desc"]
                    ],
                    "pageLength": 5,
                    "scrollX": true,
                    "lengthMenu": [
                        [5, 10, 25, 50, 100, 500, 1000, -1],
                        [5, 10, 25, 50, 100, 500, 1000, "All"]
                    ]
                })
                $('#datatable-buttons').DataTable({ destroy: true,
                    dom: "<'row'<'col-sm-12 col-md-2'l><'col-sm-12 col-md-7'B><'col-sm-12 col-md-3'f>>rtip",
                    buttons: [{
                        extend: "excel",
                        className: "btn-primary"
                    }, ],

                    order: [
                        [1, "desc"]
                    ],
                    "pageLength": 100,
                    "scrollX": true,
                    "lengthMenu": [
                        [5, 10, 25, 50, 100, 500, 1000, -1],
                        [5, 10, 25, 50, 100, 500, 1000, "All"]
                    ]
                })
                $('#encounterWSDLDatatable').DataTable({ destroy: true,
					
                    dom: "<'row'<'col-sm-12 col-md-2'l><'col-sm-12 col-md-7'><'col-sm-12 col-md-3'f>>rtip",

                    "bInfo": false,
                    order: [
                        [1, "desc"]
                    ],
                    scrollY: '50vh',
                    scrollCollapse: true,
                    paging: false
                })

                $('#encounterBAMDatatable').DataTable({ destroy: true,
                    order: [
                        [0, "desc"]
                    ],
                    "bInfo": false,
                    scrollCollapse: true,
                    paging: false
                })
                $('#encounterSOADatatable').DataTable({ destroy: true,
                    "bInfo": false,
                    order: [
                        [0, "desc"]
                    ],
                    scrollY: '50vh',
                    scrollCollapse: true,
                    "bFilter": false,
                    paging: false,

                })
                $('#EligibilityDatatable').DataTable({ destroy: true,
                    dom: "<'row'<'col-sm-12 col-md-2'l><'col-sm-12 col-md-7'B><'col-sm-12 col-md-3'f>>rtip",
                    buttons: [{
                        extend: "excel",
                        className: "btn-primary"
                    }, ],

                    order: [
                        [1, "desc"]
                    ],
                    scrollY: '50vh',
                    scrollCollapse: true,
                    paging: false
                })
                //creating table with button
                this.createDataTableButtons();

            },
            //init
            $.DataTable = new DataTable, $.DataTable.Constructor = DataTable
    }(window.jQuery),

    //initializing
    function($) {

        $.DataTable.init();
    }(window.jQuery);




});