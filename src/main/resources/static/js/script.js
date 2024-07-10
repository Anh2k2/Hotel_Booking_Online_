const toggleSidebar = () => {

	if ($(".sidebar").is(":visible")) {
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");

	}
	else {
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");

	}
};

function deleteType(typeId) {
	swal({
		title: "Are you sure?",
		text: "You want to delete this Type..!",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/admin/deleteType/" + typeId;
			} else {
				swal("Your Type is safe!");
			}
		});

}

function deleteRoom(roomId) {
	swal({
		title: "Are you sure?",
		text: "You want to delete this room..!",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/admin/deleteRoom/" + roomId;
			} else {
				swal("Your Room  is safe!");
			}
		});

}

function deleteBooking(bookId) {
	swal({
		title: "Are you sure?",
		text: "You want to delete this booking..!",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				window.location = "/admin/deleteBooking/" + bookId;
			} else {
				swal("Your Booking  is safe!");
			}
		});

}

function bookRoom() {
	// Lấy các giá trị từ các input field
	//var customerId = document.getElementById("id").value;
	var customerName = document.getElementById("user").value;
	//var customerPhone = document.getElementById("phone").value;
	//var customerEmail = document.getElementById("email").value;
	var roomId = document.getElementById("room").value;
	//var roomPrice = document.getElementById("price").value;
	var checkIn = document.getElementById("checkIn").value;
	var checkOut = document.getElementById("checkOut").value;
	//var discount = document.getElementById("discount").value;
	//var totalMoney = document.getElementById("totalMoney").value;

	// Tạo đối tượng đơn đặt phòng
	var booking = {
		//customerId: customerId,
		customerName: customerName,
		//customerPhone: customerPhone,
		//customerEmail: customerEmail,
		roomId: roomId,
		//roomPrice: roomPrice,
		checkIn: checkIn,
		checkOut: checkOut,
		//discount: discount,
		//totalMoney: totalMoney
	};

	// Gửi đơn đặt phòng đến server bằng AJAX
	$.ajax({
		type: "POST",
		url: "/user/addBooking",
		data: JSON.stringify(booking),
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function(data) {
			// Xử lý kết quả trả về từ server
			console.log("Đặt phòng thành công!");
			// Đóng modal hoặc thực hiện các thao tác khác
		},
		error: function(xhr, status, error) {
			// Xử lý lỗi
			console.log("Đặt phòng thất bại: " + error);
		}
	});
}


function showEditForm(fieldToEdit) {
	// Ẩn thông tin hiện tại
	document.getElementById(`user-${fieldToEdit}`).style.display = 'none';

	// Hiện form sửa đổi
	const editForm = document.createElement('div');
	editForm.classList.add('form-group');
	editForm.innerHTML = `
    <label for="${fieldToEdit}">New ${fieldToEdit.charAt(0).toUpperCase() + fieldToEdit.slice(1)}</label>
    <input type="text" class="form-control" id="${fieldToEdit}" value="${document.getElementById(`user-${fieldToEdit}`).textContent}">
    <button class="btn btn-primary btn-sm mt-2" onclick="saveChanges('${fieldToEdit}')">Save</button>
    <button class="btn btn-secondary btn-sm mt-2" onclick="cancelEdit('${fieldToEdit}')">Cancel</button>
  `;
	document.getElementById(`user-${fieldToEdit}`).parentNode.appendChild(editForm);
}

function saveChanges(fieldToEdit) {
	// Lấy giá trị mới từ trường input
	const newValue = document.getElementById(fieldToEdit).value;

	// Cập nhật lại thông tin hiển thị trên giao diện
	document.getElementById(`user-${fieldToEdit}`).textContent = newValue;
	document.getElementById(`user-${fieldToEdit}`).style.display = 'inline';

	// Xóa form sửa đổi
	document.getElementById(fieldToEdit).parentNode.remove();

	// Gửi request AJAX lên server để cập nhật thông tin người dùng
	// (Ví dụ sử dụng Fetch API)
	fetch('/update-user', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			[fieldToEdit]: newValue
		})
	})
		.then(response => response.json())
		.then(data => {
			// Xử lý phản hồi từ server nếu cần
		})
		.catch(error => {
			console.error('Error:', error);
		});
}

function cancelEdit(fieldToEdit) {
	// Hiện lại thông tin cũ
	document.getElementById(`user-${fieldToEdit}`).style.display = 'inline';

	// Xóa form sửa đổi
	document.getElementById(fieldToEdit).parentNode.remove();
}
