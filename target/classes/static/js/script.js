
console.log("This is the script file");

const togglesidebar = () => {
	if ($('.sidebar').is(":visible")) {
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "2%");
	} else {
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
	}
};

const search = () => {

	let query = $("#search-input").val();

	if (query == '') {
		$(".search-result").hide();
	} else {
		console.log(query);

		/*sending request to server*/
		let url = `http://localhost:8082/user/search/${query}`;

		fetch(url)
			.then((response) => {
				if (!response.ok) {
					throw new Error('Network response was not ok');
				}
				return response.json();

			})
			.then((data) => {
				console.log(data);
				let text= `<div class='list-group'>`;
				data.forEach((contact)=>{
					text+=`<a href="/user/${contact.id}/show-contact" class='list-group-item list-group-action'>${contact.name}</>`
				});
				text+=`</div>`;
				$(".search-result").html(text);
				$(".search-result").show();
			});
		
	}
};

document.addEventListener('DOMContentLoaded', () => {
    const links = document.querySelectorAll(' .item');

    links.forEach(link => {
        link.addEventListener('click', function(event) {
			
            event.preventDefault(); // Prevent the default action of the anchor tag
            
            // Remove 'active' class from all links
            links.forEach(l => l.classList.remove('active'));
            
            // Add 'active' class to the clicked link
            this.classList.add('active');

            // Optionally, you can navigate to the link's href attribute
			window.location.href = this.getAttribute('href');
        });
		
    });
	
});
