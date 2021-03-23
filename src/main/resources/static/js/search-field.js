const productList = document.getElementById('productList');
const searchField = document.getElementById('searchInput');
const customErrorField = document.getElementById('customError');

const allProducts = [];

fetch("http://localhost:8080/products/api").then(response => response.json()).then(data => {
    for (let product of data) {
        allProducts.push(product);
    }

})

searchField.addEventListener("keyup", (e) => {
    const searchingCharacters = searchField.value.toLowerCase();
    let filteredProducts = allProducts.filter(product => {
        // console.log(product);
        // console.log(product.brand);
        return product.brand.toLowerCase().includes(searchingCharacters)
            || product.model.toLowerCase().includes(searchingCharacters)
            || product.categoryName.toLowerCase().includes(searchingCharacters);
    })

    if (filteredProducts.length === 0) {
        customErrorField.style.display = "block";
    } else {
        displayProducts(filteredProducts);
    }
})

const displayProducts = (products) => {
    productList.innerHTML = products
        .map((prod) => {
            return `<div class="u-container-style u-layout-cell u-size-15 u-size-30-md u-layout-cell-1" style="height: 400px; margin-bottom: 5%;">
         <div class="u-container-layout u-container-layout-1">
                 <div class="u-blog-post u-container-style u-repeater-item u-white u-repeater-item-1" >
                          <div class="u-container-layout u-similar-container u-valign-top u-container-layout-1">
                               <h4>
                                    <a class="u-post-header-link">${prod.brand}</a>
                               </h4>
                               <a class="u-post-header-link"">${prod.model}</a>
                               <a class="u-post-header-link">
                                    <img alt="" class="u-blog-control u-expanded-width u-image u-image-default u-image-1" src="${prod.imageUrl}">
                               </a>
                               <div class="u-blog-control u-post-content u-text u-text-2 fr-view">${prod.details.substring(0, 30)}...</div>
                               <a href="/products/details/${prod.id}" class="u-blog-control u-border-2 u-border-palette-1-base u-btn u-btn-rectangle u-button-style u-none u-btn-1">View product</a>
                                </div>
                            </div>
                        </div>
                    </div>`
        })
        .join('');

}
