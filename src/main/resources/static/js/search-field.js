const productList = document.getElementById('productList');
const searchField = document.getElementById('searchInput');
const customErrorField = document.getElementById('customError');
const foundItems = document.getElementById('foundItems');
const dropDownMenu = document.getElementById('dpSelector');

const allProducts = [];

fetch("http://localhost:8080/products/api").then(response => response.json()).then(data => {
    for (let product of data) {
        allProducts.push(product);
    }
})

searchField.addEventListener("keyup", (e) => {

    const searchingCharacters = searchField.value.toLowerCase();
    let filteredProducts = allProducts.filter(product => {
        return product.brand.toLowerCase().includes(searchingCharacters)
            || product.model.toLowerCase().includes(searchingCharacters)
            || product.categoryName.toLowerCase().includes(searchingCharacters);
    })

    if (filteredProducts.length === 0) {
        customErrorField.style.display = "block";
        setTimeout(function() {
            $('#customError').fadeOut('fast');
        }, 1000);
    } else {
        foundItems.style.display = "block";
        foundItems.style.display = "block";
        setTimeout(function() {
            $('#foundItems').fadeOut('fast');
        }, 2000);
        displayProducts(filteredProducts);
    }
})

const displayProducts = (products) => {
    let foundItemsCounter = 0;
    productList.innerHTML = products
        .map((prod) => {
            foundItemsCounter +=1;
            return `     <div class="u-container-style u-layout-cell u-size-15 u-size-30-md u-layout-cell-1">
                            <div class="u-container-layout u-container-layout-1">
                                <div class="u-blog-post u-container-style u-repeater-item u-white u-repeater-item-1">
                                    <div class="u-container-layout u-similar-container u-valign-top u-container-layout-1">
                                    <h5>
                                        <a class="u-post-header-link" style="font-size: 18px; color: darkred">Type: ${prod.categoryName}</a>
                                        </h5>
                                        <a class="u-post-header-link" style="color: green">Brand: ${prod.brand}</a><br/>
                                        <a class="u-post-header-link" style="color: mediumpurple">Model: ${prod.model}</a>
                                        <a class="u-post-header-link"><img alt="" style="height:200px; width:auto; max-width:170px; object-fit: contain" src="${prod.imageUrl}"></a>
                                        <a class="u-post-header-link" style="color: forestgreen">Price: ${prod.price}.00€</a>
                                        <a href="/products/details/${prod.id}"
                                           class="u-blog-control u-border-2 u-border-palette-1-base u-btn u-btn-rectangle u-button-style u-none u-btn-1">View product</a>
                                </div>
                            </div>
                        </div>
                    </div>`
        })
        .join('');

    foundItems.innerText = 'Found ' + foundItemsCounter + ' products:';

}


function myFunction() {

    const selectedCategory = dropDownMenu.value.toLowerCase();
    console.log(selectedCategory);
    let filteredProducts = allProducts.filter(product => {
        return product.categoryName.toLowerCase().includes(selectedCategory);
    })

    if (filteredProducts.length === 0) {
        customErrorField.style.display = "block";
        setTimeout(function() {
            $('#customError').fadeOut('fast');
        }, 1000);
    } else {
        foundItems.style.display = "block";
        setTimeout(function() {
            $('#foundItems').fadeOut('fast');
        }, 2000);
        displayProducts(filteredProducts);
    }
}


