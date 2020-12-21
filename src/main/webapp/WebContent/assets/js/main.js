$(document).ready(function() {
    "user strict";

    // Menu List

    if ($(".menu-list").length) {
        $(".menu-list").slimScroll({
            height: "100%",
        });
    }
    // header

    if ($(".header").length) {
        $(window).scroll(function() {
            if ($(".header").offset().top > 200) {
                $(".header").addClass("header-collapse");
            } else {
                $(".header").removeClass("header-collapse");
            }
        });
    }




    /* menu js **/

    if ($(".dropdown-menu a.dropdown-toggle").length) {
        $(".dropdown-menu a.dropdown-toggle").on("click", function(e) {
            if (!$(this)
                .next()
                .hasClass("show")
            ) {
                $(this)
                    .parents(".dropdown-menu")
                    .first()
                    .find(".show")
                    .removeClass("show");
            }
            var $subMenu = $(this).next(".dropdown-menu");
            $subMenu.toggleClass("show");

            $(this)
                .parents("li.nav-item.dropdown.show")
                .on("hidden.bs.dropdown", function(e) {
                    $(".dropdown-submenu .show").removeClass("show");
                });

            return false;
        });
    }

    /* scroll nav js **/

    if ($("#scroll-nav a ").length) {
        $("#scroll-nav a").on("click", function() {
            if (
                location.pathname.replace(/^\//, "") ==
                this.pathname.replace(/^\//, "") &&
                location.hostname == this.hostname
            ) {
                var target = $(this.hash);
                target = target.length ?
                    target :
                    $("[name=" + this.hash.slice(1) + "]");
                if (target.length) {
                    $("html, body").animate({
                            scrollTop: target.offset().top - 0,
                        },
                        1500
                    );
                    return false;
                }
            }
        });
    }
    // sidenav

    if ($(".sidebar-nav-fixed a").length) {
        $(".sidebar-nav-fixed a")
            // Remove links that don't actually link to anything

        .click(function(event) {
            // On-page links
            if (
                location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') &&
                location.hostname == this.hostname
            ) {
                // Figure out element to scroll to
                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                // Does a scroll target exist?
                if (target.length) {
                    // Only prevent default if animation is actually gonna happen
                    event.preventDefault();
                    $('html, body').animate({
                        scrollTop: target.offset().top - 90
                    }, 1000, function() {
                        // Callback after animation
                        // Must change focus!
                        var $target = $(target);
                        $target.focus();
                        if ($target.is(":focus")) { // Checking if the target was focused
                            return false;
                        } else {
                            $target.attr('tabindex', '-1'); // Adding tabindex for elements not focusable
                            $target.focus(); // Set focus again
                        };
                    });
                }
            };
            $('.sidebar-nav-fixed a').each(function() {
                $(this).removeClass('active');
            })
            $(this).addClass('active');



        });
    }

    // datepicker js

    if ($("#program-date").length) {
        var picker = new Lightpick({
            field: document.getElementById("program-date"),
            onSelect: function(date) {
                document.getElementById("result-1").innerHTML = date.format(
                    "Do MMMM YYYY"
                );
            },
        });
    }

    // leaflet

    if ($("#mapid").length) {
        var mymap = L.map("mapid").setView([51.505, -0.09], 13);

        L.tileLayer(
            "https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw", {
                maxZoom: 18,
                attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
                    '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
                    'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
                id: "mapbox/streets-v11",
                tileSize: 512,
                zoomOffset: -1,
            }
        ).addTo(mymap);
    }

    // magnific popup

    if ($(".popup-youtube").length) {
        $(".popup-youtube").magnificPopup({
            disableOn: 700,
            type: "iframe",
            mainClass: "mfp-fade",
            removalDelay: 160,
            preloader: false,
            fixedContentPos: false,
        });
    }

    // Tooltip
    if ($('[data-toggle="tooltip"]').length) {
        $('[data-toggle="tooltip"]').tooltip();
    }

    // Popover

    if ($('[data-toggle="popover"]').length) {
        $('[data-toggle="popover"]').popover();
    }
    // Price ranger
    if ($('#price_ranger').length) {
        $("#price_ranger").ionRangeSlider({
            type: "double",
            grid: true,
            min: 0,
            max: 40,
            from: 5,
            to: 32,
            prefix: "$"
        });
    }
    if ($(".cc-inputmask").length) {
        $(".cc-inputmask").inputmask("9999 9999 9999 9999")
    }

    if ($(".cvv").length) {
        $(".cvv").inputmask("999")


    }
    if ($('[href="#"]').length) {
        document.querySelectorAll('[href="#"]').forEach(function(link) {
            link.addEventListener('click', function(e) {
                e.preventDefault()
            })
        })
    }


});