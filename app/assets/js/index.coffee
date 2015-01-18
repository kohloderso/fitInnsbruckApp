$ ->
  $.get "/users", (users) ->
    $.each users, (index, user) ->
      $('#users').append $("<li>").text user.name