const productList = document.getElementById('productList');
const searchField = document.getElementById('searchInput');

const allProducts = [];

fetch("http://localhost:8080/products/api").then(response => response.json()).then(data => {
    for (let product of data) {
        allProducts.push(product);
    }

})

// < div className = "u-container-style u-layout-cell u-size-15 u-size-30-md u-layout-cell-1" >
//     < div className = "u-container-layout u-container-layout-1" >
//     < !--blog_post-- >
//     < div className = "u-blog-post u-container-style u-repeater-item u-white u-repeater-item-1" >
//     < div className = "u-container-layout u-similar-container u-valign-top u-container-layout-1" >
//     < !--blog_post_header-- >
//     < h4 className = "u-blog-control u-text u-text-1" >
//     < a className = "u-post-header-link"
// href = "../blog/post.html" >
//     < !--blog_post_header_content-- > Post 1 Headline
// < !--/blog_post_header_content-->
// </a>
// </h4>
// <!--/blog_post_header-->
// <!--blog_post_image-->
// <a className="u-post-header-link" href="../blog/post.html"><img alt=""
// className="u-blog-control u-expanded-width u-image u-image-default u-image-1"
// src="images/1.jpeg"></a>
// <!--/blog_post_image-->
// <!--blog_post_content-->
// <div className="u-blog-control u-post-content u-text u-text-2 fr-view">Sample small
// text. Lorem ipsum
// dolor sit
// amet.
// </div>
// <!--/blog_post_content-->
// <!--blog_post_readmore-->
// <a href="../blog/post.html"
// className="u-blog-control u-border-2 u-border-palette-1-base u-btn u-btn-rectangle u-button-style u-none u-btn-1">
// <!--blog_post_readmore_content-->Read More
// <!--/blog_post_readmore_content-->
// </a>
// <!--/blog_post_readmore-->
// </div>
// </div>
// </div>
// </div>