# Lab Report, Code Quality and Design Quality

Repository URL:

## Tools

| Item | Version or name |
| --- | --- |
| JDK | |
| Maven | |
| IDE | |
| SonarQube for IDE | |
| AI tool and exact model | |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 7586cdf

Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 |java:S106|Main.java:9|Replace this use of System.out by a logger.|
| 2 |java:S106|Main.java:15|Replace this use of System.out by a logger.|
| 3 |java:S106|Main.java:17|Replace this use of System.out by a logger.|
| 4 |java:S106|Main.java:22|Replace this use of System.out by a logger.|

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date |21/10/2026 |
| AI tool and exact model |ChatGPT (GPT-6)|
| Commit reviewed |7586cdf|
| How the AI read the files (attached the files, opened the folder, or pasted the files) |Attached 4 Java files directly to ChatGPT (Locker.java, Main.java, Parcel.java, ParcelService.java)|
| Output file in the reviews folder |reviews/parcelpoint-review.md|

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | Main.java:9 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used, but console output is intentional in this CLI application. |
| 2 | Main.java:15 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used to display the store result. |
| 3 | Main.java:17 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used to display the pickup fee. |
| 4 | Main.java:22 | System.out used instead of a logger | SonarQube | java:S106 | Yes – System.out is used to display locker information. |
| 5 | Main.java:6–29 | Scanner is never closed | AI | Resource handling | Yes – The Scanner is created without being closed. |
| 6 | ParcelService.java:74–81 | FileWriter is never closed | AI | Resource handling | Yes – The writer is not closed, which may cause data loss. |
| 7 | ParcelService.java:42 | Strings are compared using `==` instead of `.equals()` | AI | String comparison | Yes – `==` compares references rather than string contents. |
| 8 | Main.java:11–17 | Input arguments are not validated | AI | Input validation | Yes – Missing arguments may cause exceptions. |
| 9 | ParcelService.java:39–54 | Returning 0 for a missing parcel is ambiguous | AI | Ambiguous result | Yes – A valid pickup can also have a zero fee. |
| 10 | ParcelService.java:75–81 | Empty catch block ignores IOException | AI | Error handling | Yes – Errors are silently ignored. |
| 11 | ParcelService.java:6 | Unused Date import | AI | Unused import | Yes – Date is imported but never used. |
| 12 | ParcelService.java:12 | Unused maxParcels field | AI | Unused field | Yes – The field is declared but never read. |
| 13 | Main.java:6–25 | Multiple commands handled in one method | AI | Logical cohesion | No – Command dispatch is an intended responsibility of the CLI entry point; branching alone does not prove poor design. |
| 14 | ParcelService.java:64–71 | Boolean parameter controls different reporting behaviours | AI | Logical cohesion | Yes – The flag selects between reporting behaviours. |
| 15 | ParcelService.java:24–80 | Class handles storage, fees, notifications and file persistence | AI | Class cohesion | Yes – Multiple responsibilities are combined in one class. |
| 16 | Main.java:19–21; ParcelService.java:64–68 | Boolean argument controls ParcelService behaviour | AI | Control coupling | Yes – The caller controls which reporting branch executes. |
| 17 | Parcel.java:7 | Public status field allows direct modification | AI | Content coupling | Yes – Other classes can directly access the field. |
| 18 | ParcelService.java:20–21; Main.java:22 | Internal locker collection is returned directly | AI | Strong coupling / collection exposure | Yes – Callers can modify the internal list. |
| 19 | ParcelService.java:52–59 | Entire Parcel object passed when only size is needed | AI | Stamp coupling | Yes – The fee method only requires the parcel size. |
| 20 | Parcel.java:13; ParcelService.java:28,44,68 | Hardcoded strings represent parcel statuses | AI | Connascence of convention | Yes – Classes depend on matching status strings. |
| 21 | Locker.java:5; Parcel.java:6 | Shared numeric codes represent parcel sizes | AI | Connascence of convention | Yes – Classes must agree on size codes 1, 2 and 3. |
| 22 | Main.java:13; Parcel.java:9–12 | Parcel constructor depends on argument order | AI | Connascence of position | Yes – Two String arguments can be accidentally swapped. |
| 23 | ParcelService.java:15–17; Locker.java:8–10 | Locker constructor depends on integer argument order | AI | Connascence of position | Yes – Swapping the two integers would still compile. |
| 24 | ParcelService.java:36; Main.java:15 | Null represents no available locker | AI | Connascence of convention | Yes – Both classes must understand the meaning of null. |
  

### 2.1 Name two problems that only the AI review found.
-Incorrect String Comparison: In ParcelService.java, the code uses == instead of .equals() to compare strings. This can cause problems because == checks whether two objects are the same, not whether their text is the same.
-Control Coupling: In Main.java and ParcelService.java, a Boolean value is used to control how the method works. This creates control coupling because one class controls the behavior of another class.
### 2.2 Name one problem that only SonarQube found, or write none.
SonarQube found that `Main.java` uses `System.out` instead of a logger (java:S106), but the AI did not mention this problem. However, I think using `System.out` is reasonable because this is a command-line program.
### 2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
I think the AI finding about logical cohesion in `Main.java` was too general. The AI said that handling different commands in one method is a design problem. However, I think it makes sense because the main method needs to handle user commands in this program. The code could be improved, but I don't think this automatically means the design is bad.
## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 | Kept | `System.out.println()` displays instructions to users in this command-line application. |
| 2 | Kept | `System.out.println()` displays the locker number after storing a parcel. This is intended CLI output. |
| 3 | Kept | `System.out.println()` displays the pickup fee to the user. A logger is not necessary for this output. |
| 4 | Kept | `System.out.println()` displays the number of free lockers. This is intended CLI output. |


Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| Control Coupling | I replaced `printParcels(boolean forStaff)` with two separate methods, `printStaffParcels()` and `printWaitingParcels()`, to remove the Boolean flag that controlled the method's behavior. | `88c7488` |
| Content Coupling | I changed `Parcel.status` from public to private, added `getStatus()` and `setStatus()`, and updated `ParcelService.java` to use these methods instead of accessing the status directly. | `0fee7da` |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
