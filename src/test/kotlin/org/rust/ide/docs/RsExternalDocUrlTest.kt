/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.docs

import com.intellij.testFramework.LightProjectDescriptor

class RsExternalDocUrlTest : RsDocumentationProviderTest() {

    override fun getProjectDescriptor(): LightProjectDescriptor = WithStdlibAndDependencyRustProjectDescriptor

    fun `test not stdlib item`() = doUrlTestByFileTree("""
        //- dep-lib/lib.rs
        pub struct Foo;
                  //^
    """, "https://docs.rs/dep-lib/0.0.1/dep_lib_target/struct.Foo.html")

    fun `test private item`() = doUrlTestByFileTree("""
        //- dep-lib/lib.rs
        struct Foo;
              //^
    """, null)

    fun `test pub item in private module`() = doUrlTestByFileTree("""
        //- dep-lib/lib.rs
        mod foo {
            pub struct Foo;
                       //^
        }
    """, null)

    fun `test doc hidden`() = doUrlTestByFileTree("""
        //- dep-lib/lib.rs
        #[doc(hidden)]
        pub fn foo() {}
               //^
    """, null, RsDocumentationProvider.Testmarks.docHidden)

    fun `test macro`() = doUrlTestByFileTree("""
        //- dep-lib/lib.rs
        #[macro_export]
        macro_rules! foo {
                    //^
            () => { unimplemented!() };
        }
    """, "https://docs.rs/dep-lib/0.0.1/dep_lib_target/macro.foo.html")

    fun `test not exported macro`() = doUrlTestByFileTree("""
        //- dep-lib/lib.rs
        macro_rules! foo {
                    //^
            () => { unimplemented!() };
        }
    """, null, RsDocumentationProvider.Testmarks.notExportedMacro)
}
